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
