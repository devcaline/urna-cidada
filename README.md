# 🗳️ Urna Cidadã

**Simulação educativa do voto eletrônico e combate à desinformação em ano eleitoral.**

Simulador de urna eletrônica feito em **Java**, com interface gráfica em **Swing** e dados salvos na nuvem com **Firebase (Cloud Firestore)**. O sistema reproduz a lógica da urna usada nas eleições brasileiras, com votação em sequência para dois cargos: um **proporcional** (ex.: vereador ou deputado) e um **majoritário** (ex.: prefeito ou presidente).

> Projeto de **Extensão Curricular** da disciplina de **Programação Orientada a Objetos (POO)** — Ciência da Computação, UNIJORGE, semestre 2026.2.

## 🎯 Sobre o projeto

2026 é ano de eleições gerais, e muitos jovens vão votar pela primeira vez cercados de conteúdo falso ou manipulado, inclusive *deepfakes* gerados por IA. O projeto leva o simulador desenvolvido em sala para **oficinas com estudantes do 2º e 3º ano do Ensino Médio** de escolas públicas de Salvador, onde eles operam a urna e participam de uma roda de conversa sobre o processo eleitoral e a desinformação.

A ação é **apartidária**: todos os partidos e candidatos da simulação são **fictícios**, sem vínculo com legendas ou candidatos reais.

## 🎬 Vídeo de apresentação

Material complementar para a apresentação do projeto de extensão:

[![Vídeo de apresentação da Urna Cidadã](https://img.youtube.com/vi/XhsV7PPJD10/hqdefault.jpg)](https://youtu.be/XhsV7PPJD10)

▶️ Assista no YouTube: <https://youtu.be/XhsV7PPJD10>

## ✨ Funcionalidades

- Votação em sequência para dois cargos: Deputado Federal (4 dígitos) e Presidente (2 dígitos)
- Teclado numérico com **Corrige**, **Branco** e **Confirma**, como na urna do TSE
- Exibição de nome, partido, foto e vice do candidato antes da confirmação
- Voto **nulo** (número inexistente) e voto **em branco**
- Votação contínua, um eleitor após o outro
- Área do mesário com senha: abrir a sessão com conferência dos votos zerados, encerrar a votação e consultar a contagem
- Votos gravados no Firebase Cloud Firestore, com modo **ENSAIO** para testes e **OFICINA** para a votação real
- Apuração com votos e percentuais por candidato, brancos e nulos

## 🧱 Conceitos de POO aplicados

| Conceito | Onde |
|---|---|
| **Encapsulamento** | `Candidato`, `Cargo`, `Partido` e `Voto` são imutáveis e validam os dados no construtor; `VotacaoEleitor` só muda de estado por métodos com regras; `Mesario` guarda a senha sem expô-la |
| **Herança** | `RepositorioVotosFirestore` estende `RepositorioVotosBase`; `ErroRepositorioException` estende `RuntimeException` |
| **Polimorfismo** | a urna trabalha com a interface `RepositorioVotos` sem depender do Firebase; sobrescrita de `gravar()` e `listar()`; sobrecarga dos construtores de `Candidato` |
| **Abstração** | interface `RepositorioVotos`, classe abstrata `RepositorioVotosBase`, `VotacaoEleitor` e `SessaoVotacao` separadas da interface gráfica |

## 🛠️ Tecnologias

| Tecnologia | Uso |
|------------|-----|
| Java | Linguagem principal |
| Java Swing | Interface gráfica da urna |
| Firebase — Cloud Firestore | Banco de dados NoSQL na nuvem |

## 📂 Estrutura do repositório

```text
urna-cidada/
├── docs/                                  # Casos de uso, requisitos e modelagem Firestore
├── urna/
│   └── projeto_urna/                      # Projeto Java Maven principal
│       ├── pom.xml                        # Configurações do Maven e dependências (Firebase, JUnit)
│       └── src/
│           ├── main/
│           │   ├── java/br/edu/unijorge/urnacidada/
│           │   │   ├── dados/             # Catálogo inicial de partidos e candidatos fictícios
│           │   │   ├── dominio/           # Modelos de domínio (Candidato, Cargo, Partido, Voto, Mesario)
│           │   │   ├── infraestrutura/    # Conexão e repositório Firebase Cloud Firestore
│           │   │   ├── repositorio/       # Interfaces e contratos de persistência
│           │   │   ├── servico/           # Regras de votação (VotacaoEleitor) e apuração
│           │   │   └── visao/             # Telas Java Swing (Menu, Urna, Contagem, Exibição)
│           │   └── resources/
│           │       └── assents/           # Fotos dos candidatos e sons da urna (.wav)
│           └── test/                      # Testes unitários com JUnit 5
└── README.md
```

## ▶️ Como rodar

1. Instale o **JDK 17 ou superior** e abra a pasta `urna-cidada` no IntelliJ (projeto Maven em `urna/projeto_urna`).
2. Coloque a chave do Firebase (`*firebase-adminsdk*.json`) e o arquivo `senha-mesario.txt` na pasta raiz `urna-cidada/`. Os dois são pedidos à equipe em mensagem privada e nunca vão para o GitHub (veja [docs/banco-de-dados.md](docs/banco-de-dados.md)).
3. Rode a classe `br.edu.unijorge.urnacidada.visao.Menu`.
4. Escolha **ENSAIO** para testar ou **OFICINA** para a votação real.
5. Testes automáticos: aba Maven → Lifecycle → `test`.

## 📌 Resumo das Implementações

O projeto **Urna Cidadã** foi estruturado e refatorado aplicando as boas práticas de **Programação Orientada a Objetos (POO)** e separação em camadas, integrando interface gráfica (Swing), persistência em nuvem (Firebase Firestore) e recursos multimídia.

### 🏛️ Destaques da Estrutura

- **Camada de Visão unificada (`visao`):** todas as telas Swing foram consolidadas no pacote `br.edu.unijorge.urnacidada.visao`, eliminando conflitos de pacotes e desacoplando a interface da regra de negócio.
- **Camada de Domínio e Serviços (`dominio` / `servico`):** validações estritas de número de dígitos por cargo, suporte a votos nominais, votos em branco e nulos, e cálculo automatizado de percentuais de apuração sem divisão por zero.
- **Persistência Segura (`infraestrutura`):** integração com o Firebase Admin SDK no Firestore, garantindo idempotência de votos (UUID único por voto para evitar duplicidade em falhas de rede) e anonimato do eleitor.
- **Recursos Multimídia:** fotos dinâmicas de candidatos e vice-candidatos, e reprodução de efeitos sonoros característicos da urna do TSE (`confirma-urna.wav` e `ok.wav`).

---

### 🔄 Controle de Sessão: Abertura e Encerramento

1. **Seleção de modo:** ao iniciar pelo `Menu` (pacote `visao`), o sistema pergunta o modo de operação:
   - **ENSAIO (teste):** grava na sessão `ensaio`, para testes e demonstrações.
   - **OFICINA (votação real):** grava na sessão oficial `oficina-2026`.
2. **Abertura da sessão:** o botão **"Abrir Sessão"** pede a senha do mesário e mostra a **conferência inicial** com os votos já registrados. Na oficina, a votação só é liberada com a contagem zerada (RN16).
3. **Votação contínua:** primeiro **Deputado Federal** (4 dígitos), depois **Presidente** (2 dígitos). Ao confirmar o último cargo, o voto é gravado, o som do TSE toca, a tela de **FIM** aparece e, em seguida, a urna abre para o próximo eleitor.
4. **Encerramento:** só o mesário encerra, pelo botão **"Mesário"** da urna, com senha e no intervalo entre um eleitor e outro. Depois disso, a **Contagem de Votos** (também protegida por senha) mostra a apuração.

## 📋 Etapas

- [x] Formação do grupo (até 5 integrantes) e definição do escopo
- [x] Modelagem: casos de uso, classes e dados no Firebase
- [x] Desenvolvimento do simulador (Swing + Firebase)
- [ ] Material educativo sobre fake news e conteúdos gerados por IA
- [x] Testes internos e ajustes de usabilidade
- [ ] Oficina na escola parceira
- [ ] Questionário de percepção (antes e depois da oficina)
- [ ] Relatório final

## 📅 Entregas (Canvas)

Todas as entregas vão até **30/11/2026**.

| Desafio | Entrega |
|:-------:|---------|
| 1 | Cronograma de atividades (modelo institucional) e protótipo funcional documentado neste repositório |
| 2 | Correlação com os ODS: **ODS 16** (metas 16.6, 16.7 e 16.10) e **ODS 4** (meta 4.7) |
| 3 | Relatório final da ação de extensão |

## 👥 Equipe

° Giovana Gusmão Santos

° Iasmin Luise Santos da Silva

° Igor Jerônimo Rosário de Almeida

° Monique Amorim Mendes

° Victória Caroline Bittencourt Costa

° William Silva Lago

## 🔒 Segurança

As credenciais do Firebase (arquivos de *service account* e `google-services.json`) **não devem ser enviadas para o repositório**. O `.gitignore` já bloqueia esses arquivos. Cada integrante guarda a sua cópia localmente.

## 🗄️ Banco de dados

Estrutura do Firestore, decisões de modelagem e passo a passo para conectar a urna ao Firebase: [docs/banco-de-dados.md](docs/banco-de-dados.md).

## 📚 Referências

- [TSE — Simulador de votação](https://www.tse.jus.br/hotsites/simulador-de-votacao/)
- [ONU Brasil — ODS 16: Paz, Justiça e Instituições Eficazes](https://brasil.un.org/pt-br/sdgs/16)
- [ONU Brasil — ODS 4: Educação de Qualidade](https://brasil.un.org/pt-br/sdgs/4)
- [Documentação do Firebase](https://firebase.google.com/docs)
- [Documentação do Java SE](https://docs.oracle.com/javase/)
