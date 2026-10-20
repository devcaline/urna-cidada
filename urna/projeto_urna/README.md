# 🗳️ Urna Cidadã

**Simulação educativa do voto eletrônico e combate à desinformação em ano eleitoral.**

Simulador de urna eletrônica feito em **Java**, com interface gráfica em **Swing** e dados salvos na nuvem com **Firebase (Cloud Firestore)**. O sistema reproduz a lógica da urna usada nas eleições brasileiras, com votação em sequência para dois cargos: um **proporcional** (ex.: vereador ou deputado) e um **majoritário** (ex.: prefeito ou presidente).

> Projeto de **Extensão Curricular** da disciplina de **Programação Orientada a Objetos (POO)** — Ciência da Computação, UNIJORGE, semestre 2026.2.

## 🎯 Sobre o projeto

2026 é ano de eleições gerais, e muitos jovens vão votar pela primeira vez cercados de conteúdo falso ou manipulado, inclusive *deepfakes* gerados por IA. O projeto leva o simulador desenvolvido em sala para **oficinas com estudantes do 2º e 3º ano do Ensino Médio** de escolas públicas de Salvador, onde eles operam a urna e participam de uma roda de conversa sobre o processo eleitoral e a desinformação.

A ação é **apartidária**: todos os partidos e candidatos da simulação são **fictícios**, sem vínculo com legendas ou candidatos reais.

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
├── docs/          # casos de uso, modelagem de classes e dos dados no Firestore
├── src/           # código-fonte Java
├── material/      # material educativo sobre fake news e deepfakes
└── README.md
```

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

*A definir.*

## 🔒 Segurança

As credenciais do Firebase (arquivos de *service account* e `google-services.json`) **não devem ser enviadas para o repositório**. O `.gitignore` já bloqueia esses arquivos. Cada integrante guarda a sua cópia localmente.

## 📚 Referências

- [TSE — Simulador de votação](https://www.tse.jus.br/hotsites/simulador-de-votacao/)
- [ONU Brasil — ODS 16: Paz, Justiça e Instituições Eficazes](https://brasil.un.org/pt-br/sdgs/16)
- [ONU Brasil — ODS 4: Educação de Qualidade](https://brasil.un.org/pt-br/sdgs/4)
- [Documentação do Firebase](https://firebase.google.com/docs)
- [Documentação do Java SE](https://docs.oracle.com/javase/)


## Base Java — entrega de Modelagem e Estrutura

O conteúdo anterior descreve o projeto completo previsto. Nesta entrega existem somente entidades de domínio, contratos, exemplo técnico e testes. Os cargos definidos em `docs/requisitos.md` são Deputado Federal (4 dígitos) e Presidente (2 dígitos), com três candidatos fictícios por cargo a definir pela equipe. O voto nulo permanece pendente; a enumeração o reserva, mas a entidade ainda rejeita sua construção.

### Ambiente e comandos

- JDK 17 ou superior; compilação com `--release 17`.
- Maven 3.9.x; UTF-8; JUnit 5.11.4 somente para testes.
- Validação local realizada com Maven 3.9.6 e JDK 21.0.12.1. Execução em JVM 17 não foi realizada neste ambiente.

```powershell
mvn clean verify
mvn test
java -jar target/urna-cidada-0.1.0-SNAPSHOT.jar
```

`verify` compila, testa e empacota. O JAR executa apenas um exemplo técnico de associação e busca, sem eleição, confirmação ou armazenamento. Não é a aplicação Swing completa. Os dados exibidos não são candidaturas definitivas. A primeira execução Maven precisa de acesso ao Maven Central para baixar plugins/JUnit.

### VS Code

Abra a pasta que contém `pom.xml`, utilize o Extension Pack for Java e configure um JDK 17 ou superior em **Java: Configure Java Runtime**. Aguarde a importação Maven; use a visão Testing ou `mvn test` no terminal. `Main.java` pode ser executado pelo editor ou pelo JAR após `mvn clean verify`. Nenhuma credencial Firebase é necessária.

### Código disponível

Pacote-base `br.edu.unijorge.urnacidada`: `dominio.modelo` contém Partido, Cargo, Candidato, Voto e Usuario/Eleitor/Mesario; `dominio.enums` contém TipoVoto/EstadoSessao; `repositorio` contém somente a interface RepositorioVotos. Os testes ficam em `src/test/java` na mesma organização de domínio. `Validacao` é auxiliar interna do modelo.

Cargo protege sua coleção e impede duplicações por número; Candidato referencia cargo e partido. Voto representa candidato ou branco com consistência de cargo. O estado confirmado é apenas informado pelo chamador, sem operação de confirmar. A hierarquia de usuários demonstra POO sem identificar alunos nos votos ou conceder permissões.

### Próximas responsabilidades

- Lógica: fluxo individual, estados da sessão geral, confirmação, correção e coordenação de registro.
- Interface: Swing, teclado, mensagens e apresentação.
- Persistência: implementação de RepositorioVotos, esquema, IDs, idempotência e regras de acesso Firestore.
- Administração e apuração: autenticação, autorização e resultados conforme os requisitos.

Os pacotes `servico`, `interfacegrafica` e `infraestrutura` estão reservados na arquitetura, sem classes vazias ou implementação antecipada. Não há SDK Firebase, armazenamento em memória, autenticação, contagem, campanha ou sessão funcional. Os contratos operacionais ainda precisam de revisão da equipe.

### Documentação desta entrega

- [Modelagem, relacionamentos e POO](docs/modelagem-classes.md)
- [Contratos existentes e propostas futuras](docs/contratos-integracao.md)
- [Pendências e divergências](docs/decisoes-pendentes.md)
- [UML editável](docs/diagramas/classes.mmd)
- [UML visual em SVG](docs/diagramas/classes.svg)
- [Relatório da entrega e verificações](docs/relatorio-entrega.md)
- [Requisitos originais](docs/requisitos.md)
- [Diagrama original](docs/diagramas/Diagrama_Classes_Urna_Cidade.pdf)
- [Casos de uso originais](docs/diagramas/Diagrama-caso-de-uso.png)

O diretório recebido estava vazio e sem `.git`; os documentos enviados foram preservados em cópia, sem inicializar repositório, criar branch, commit ou push. Para colaboração, integrar estes arquivos ao repositório existente após revisão.
