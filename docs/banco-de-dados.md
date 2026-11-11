# Banco de Dados — Firebase Cloud Firestore

A Urna Cidadã grava os votos no **Firebase Cloud Firestore** (banco NoSQL na nuvem), atendendo ao **RF12** e à pendência **PEND07**.
O programa Java se conecta com o **Firebase Admin SDK** usando uma chave de conta de serviço.

## 1. Estrutura dos dados

```text
partidos/{sigla}                    ← partidos fictícios
cargos/{idCargo}                    ← cargos da votação, na ordem
    candidatos/{numero}             ← candidatos do cargo (o número é o ID do documento)
sessoes/{idSessao}                  ← uma sessão por oficina (padrão: oficina-2026)
    votos/{idVoto}                  ← votos confirmados daquela sessão
```

### Exemplos de documentos

`partidos/PA`
```json
{ "nome": "Partido Ação", "sigla": "PA" }
```

`cargos/presidente`
```json
{ "nome": "Presidente", "quantidadeDigitos": 2, "ordem": 2 }
```

`cargos/presidente/candidatos/43`
```json
{ "nome": "Moranguete", "numero": "43", "partido": "PUI", "vice": "Zé Noura", "foto": "assents/moranguete.jpeg" }
```

`sessoes/oficina-2026`
```json
{ "estado": "NAO_INICIADA", "criadaEm": "<data do servidor>" }
```

`sessoes/oficina-2026/votos/3f1c9a2e-...` (voto em candidato)
```json
{ "cargo": "presidente", "cargoNome": "Presidente", "tipo": "CANDIDATO",
  "numero": "43", "candidato": "Moranguete", "partido": "PUI" }
```

`sessoes/oficina-2026/votos/8b7d...` (voto em branco)
```json
{ "cargo": "deputado-federal", "cargoNome": "Deputado Federal", "tipo": "BRANCO",
  "numero": null, "candidato": null, "partido": null }
```

`sessoes/oficina-2026/votos/c42e...` (voto nulo: número completo que não é de nenhum candidato, RN10)
```json
{ "cargo": "presidente", "cargoNome": "Presidente", "tipo": "NULO",
  "numero": null, "candidato": null, "partido": null }
```

O campo `tipo` aceita `CANDIDATO`, `BRANCO` ou `NULO`. Brancos e nulos não guardam número nem candidato. No voto nulo, o número digitado também não é guardado: ele não pertence a nenhum candidato e não entra na apuração.

## 2. Decisões de modelagem

| Decisão | Por quê |
|---|---|
| **Número do candidato é o ID do documento** dentro do cargo | O Firestore não permite dois documentos com o mesmo ID, então dois candidatos não podem ter o mesmo número no mesmo cargo (**RN14**). |
| **Votos ficam dentro da sessão** (`sessoes/{id}/votos`) | Cada oficina tem a sua apuração separada; dá para começar uma oficina nova sem apagar a anterior. |
| **ID do voto é um UUID aleatório** gerado na urna | Não revela a ordem nem quem votou. |
| **Gravação com `create()`** (falha se o ID já existir) | Se a rede cair e o eleitor apertar CONFIRMA de novo, o mesmo ID é reenviado e o voto **não é duplicado** (**RNFs03**). Voto gravado nunca é alterado (**RN13**). |
| **Voto não guarda nome do eleitor, ID nem horário** | Preserva o **sigilo do voto**, como na urna real. |
| **Nome do candidato e partido repetidos no voto** | Deixa o voto legível direto no console do Firebase. A apuração usa o `numero`. |
| **Voto só é gravado depois de validado** e **antes de avançar a tela** | Só votos confirmados entram na contagem (**RN06**, **RN22**). Se a gravação falhar, o eleitor continua no mesmo cargo e o mesário é avisado. |
| **Regras do Firestore bloqueiam todo acesso externo** (`firestore.rules`) | Só o programa da urna (com a chave) lê e grava. Ninguém consegue ver resultados ou forjar votos por um navegador (**RN19**, **RN21**, **RN24**). |

## 3. Como configurar (uma vez por projeto)

### 3.1 Criar o projeto e o banco
1. Acesse <https://console.firebase.google.com> e clique em **Criar um projeto** (sugestão de nome: `urna-cidada`). O Google Analytics pode ficar desativado.
2. No menu lateral, abra **Firestore Database** e clique em **Criar banco de dados**.
   - **Local:** `southamerica-east1 (São Paulo)`. O local não pode ser mudado depois.
   - **Modo:** **produção**. Ele já bloqueia o acesso externo, igual ao nosso `firestore.rules`.
3. (Opcional) Na aba **Regras** do Firestore, cole o conteúdo de [`firestore.rules`](../firestore.rules) e clique em **Publicar**.

O plano gratuito (Spark) é suficiente: ele permite 20 mil gravações e 50 mil leituras por dia.

### 3.2 Gerar a chave do Admin SDK
1. Clique na engrenagem ⚙️ > **Configurações do projeto** > aba **Contas de serviço**.
2. Em **Firebase Admin SDK**, clique em **Gerar nova chave privada**. Será baixado um arquivo como `urna-cidada-firebase-adminsdk-abc12-1234567890.json`.
3. Salve o arquivo fora do repositório, em `%LOCALAPPDATA%\UrnaCidada\serviceAccount.json` (por exemplo, `C:\Users\<usuário>\AppData\Local\UrnaCidada\serviceAccount.json`). Crie a pasta `UrnaCidada` se ela ainda não existir.
4. Alternativamente, salve-o em outro caminho e configure `FIREBASE_CREDENCIAIS` com o caminho completo antes de iniciar a aplicação.

> ⚠️ **A chave dá acesso ao banco.** Nunca a envie para o GitHub nem para conversas ou grupos abertos. Se ela vazar, revogue-a em Google Cloud Console > IAM e administrador > Contas de serviço > Chaves e gere outra.

O programa procura a chave nesta ordem:
1. caminho em `-Dfirebase.credenciais=...` ou na variável de ambiente `FIREBASE_CREDENCIAIS`;
2. variável `GOOGLE_APPLICATION_CREDENTIALS`;
3. `%LOCALAPPDATA%\UrnaCidada\serviceAccount.json`;
4. arquivo `serviceAccount.json` ou `*firebase-adminsdk*.json` na pasta atual ou em até 3 pastas acima.

### 3.3 Preparar o banco
Rode a classe `br.edu.unijorge.urnacidada.infraestrutura.InicializarBanco` pela IDE (botão ▶ no `main`) ou pelo terminal, de dentro de `urna/projeto_urna`:

```bash
mvn -q compile exec:java -Dexec.mainClass=br.edu.unijorge.urnacidada.infraestrutura.InicializarBanco
```

Ela publica partidos, cargos e candidatos de `CatalogoCandidatos`, cria a sessão e mostra quantos votos a sessão já tem.
Antes da oficina o resultado deve ser **0**, que é a conferência inicial da **RN16**. Pode rodar quantas vezes quiser: os votos nunca são apagados.

### 3.4 Rodar a urna
Pela IDE, rode `Menu` com a pasta de trabalho em `interface/` (para as fotos aparecerem). Pelo terminal, de dentro de `interface/`:

```bash
mvn -q -f ../urna/projeto_urna/pom.xml compile exec:java "-Dexec.mainClass=Menu"
```

Ao abrir, a urna pergunta o **modo**:

| Botão | Para quê | Sessão no banco |
|---|---|---|
| **ENSAIO (teste)** | Testar e demonstrar | `ensaio` |
| **OFICINA (votação real)** | Votação com os alunos | `oficina-2026` |

O Menu Principal mostra o modo em uso: **MODO ENSAIO** em laranja ou **VOTAÇÃO REAL** em verde. Para trocar de modo, feche o programa e abra de novo.
Cada CONFIRMA cria um documento em `sessoes/<sessão>/votos`, um por cargo, e a tela **Contagem de Votos** lê os votos direto do Firestore. O rodapé dela mostra a sessão.
Se a chave não for encontrada ou o Firestore não responder, **a urna não abre**. Nenhum voto fica fora do banco.

### 3.5 Outra sessão (nova oficina)
Para uma escola nova, mude `SESSAO_PADRAO` em `BancoDeDados` ou rode `InicializarBanco` com `-Durna.sessao=nome-da-sessao` para conferir a contagem de outra sessão.

## 4. Código da persistência

| Classe | Papel |
|---|---|
| `repositorio.RepositorioVotos` | Interface (contrato) que as telas usam. |
| `repositorio.RepositorioVotosBase` | Classe abstrata: valida (voto não nulo e confirmado) e delega a gravação. |
| `infraestrutura.RepositorioVotosFirestore` | Implementação no Firestore: conversão objeto ⇄ documento, idempotência e tempo limite de 10 s. |
| `infraestrutura.ConexaoFirebase` | Localiza a chave e abre a conexão (Admin SDK). |
| `infraestrutura.BancoDeDados` | Ponto único de acesso usado pelas telas; testa a conexão ao abrir. |
| `infraestrutura.EsquemaFirestore` | Nomes das coleções e o ID do cargo (`"Deputado Federal"` → `deputado-federal`). |
| `infraestrutura.InicializarBanco` | Prepara o banco (catálogo e sessão). |
| `servico.Apuracao` | Conta votos e percentuais por cargo, sem divisão por zero (**UC09**). |

## 5. Próximos passos (módulo administrativo)
- Mudar `sessoes/{id}.estado` para `EM_ANDAMENTO` e `ENCERRADA` pelo mesário (**RF16**, **RF17**, **RN23**) e só abrir a urna com a sessão `EM_ANDAMENTO`.
- Autenticação dos mesários (**RF14**): por exemplo, uma coleção `mesarios/{id}` com a senha guardada como *hash* com *salt*, nunca em texto puro.
- Liberar a tela de Contagem só para mesário autenticado e só após o encerramento (**RN19**, **RN21**).
