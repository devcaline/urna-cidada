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

## ✨ Funcionalidades previstas

- Votação em sequência para dois cargos (proporcional e majoritário)
- Teclado numérico da urna com **Corrige**, **Branco** e **Confirma**
- Exibição do candidato (nome, número e partido) antes da confirmação
- Tratamento de voto **nulo** e voto **em branco**
- Registro dos votos no Cloud Firestore
- Apuração e exibição dos resultados

## 🧱 Conceitos de POO aplicados

O código deve ser organizado em classes e pacotes coerentes com o paradigma orientado a objetos, aplicando:

- **Encapsulamento**
- **Herança**
- **Polimorfismo**
- **Abstração**

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
├── assents/                               # Imagens dos candidatos e efeitos sonoros (.wav)
├── urna/
│   └── projeto_urna/                      # Projeto Java Maven principal
│       ├── pom.xml                        # Configurações do Maven e dependências (Firebase, JUnit)
│       └── src/
│           ├── main/
│           │   ├── java/br/edu/unijorge/urnacidada/
│           │   │   ├── dados/             # Catálogo inicial de partidos e candidatos fictícios
│           │   │   ├── dominio/           # Modelos de domínio (Candidato, Cargo, Partido, Voto, Usuario)
│           │   │   ├── infraestrutura/    # Conexão e repositório Firebase Cloud Firestore
│           │   │   ├── repositorio/       # Interfaces e contratos de persistência
│           │   │   ├── servico/           # Regras de votação (VotacaoEleitor) e apuração
│           │   │   ├── visao/             # Telas Java Swing (Menu, Urna, Contagem, Exibição)
│           │   │   └── Main.java          # Classe de entrada alternativa
│           │   └── resources/
│           │       └── assents/           # Recursos estáticos empacotados pelo Maven
│           └── test/                      # Testes unitários com JUnit 5
└── README.md
```

## 📌 Resumo das Implementações

O projeto **Urna Cidadã** foi estruturado e refatorado aplicando as boas práticas de **Programação Orientada a Objetos (POO)** e separação em camadas, integrando interface gráfica (Swing), persistência em nuvem (Firebase Firestore) e recursos multimídia.

### 🏛️ Destaques da Estrutura

- **Camada de Visão unificada (`visao`):** todas as telas Swing foram consolidadas no pacote `br.edu.unijorge.urnacidada.visao`, eliminando conflitos de pacotes e desacoplando a interface da regra de negócio.
- **Camada de Domínio e Serviços (`dominio` / `servico`):** validações estritas de número de dígitos por cargo, suporte a votos nominais, votos em branco e nulos, e cálculo automatizado de percentuais de apuração sem divisão por zero.
- **Persistência Segura (`infraestrutura`):** integração com o Firebase Admin SDK no Firestore, garantindo idempotência de votos (UUID único por voto para evitar duplicidade em falhas de rede) e anonimato do eleitor.
- **Recursos Multimídia:** fotos dinâmicas de candidatos e vice-candidatos, e reprodução de efeitos sonoros característicos da urna do TSE (`confirma-urna.wav` e `ok.wav`).

---

### 🔄 Controle de Sessão: Abertura e Encerramento

O sistema oferece gerenciamento completo do ciclo da sessão eleitoral:

1. **Seleção de Modo (Inicialização):**
   - Ao iniciar pelo [Menu.java](file:///urna/projeto_urna/src/main/java/br/edu/unijorge/urnacidada/visao/Menu.java), o sistema solicita o modo de operação:
     - **ENSAIO (teste):** direciona os registros para a sessão `ensaio`, ideal para testes de mesa e demonstrações sem poluir os dados oficiais.
     - **OFICINA (votação real):** direciona os registros para a sessão oficial da escola (`oficina-2026`).

2. **Abertura da Sessão:**
   - Através do botão **"Abrir Sessão"**, a urna verifica a conectividade com o Cloud Firestore e libera a tela de votação para o primeiro eleitor.
   - A votação segue rigorosamente as regras eleitorais: primeiro para **Deputado Federal** (4 dígitos) e em seguida para **Presidente** (2 dígitos).

3. **Encerramento da Sessão e Votação Contínua:**
   - Ao confirmar o último cargo, o voto é gravado na nuvem, o som clássico do TSE é reproduzido e a tela de **FIM** é exibida com o registro de data e hora.
   - O sistema oferece diálogo de confirmação para **Encerrar Sessão**:
     - **Sim (Encerrar):** finaliza a rodada e retorna ao Menu Principal, permitindo que os mesários abram a **Contagem de Votos** e realizem a apuração final.
     - **Não / Próximo Eleitor:** após um breve intervalo a urna reinicializa automaticamente para o próximo aluno votar, garantindo a dinâmica contínua da oficina.


## 📋 Etapas

- [ ] Formação do grupo (até 5 integrantes) e definição do escopo
- [ ] Modelagem: casos de uso, classes e dados no Firebase
- [ ] Desenvolvimento do simulador (Swing + Firebase)
- [ ] Material educativo sobre fake news e conteúdos gerados por IA
- [ ] Testes internos e ajustes de usabilidade
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
