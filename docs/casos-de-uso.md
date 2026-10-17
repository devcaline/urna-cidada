# Casos de Uso — Urna Cidadã
## 1. Objetivo
Este documento descreve como o eleitor interage com o sistema Urna Cidadã durante uma votação simulada. Os casos de uso apresentam os objetivos do usuário, o fluxo principal de interação e as situações alternativas que podem ocorrer durante a votação.
As regras detalhadas e os requisitos funcionais do sistema estão documentados no arquivo requisitos.md.

## 2. Ator principal
Eleitor: pessoa que utiliza a urna eletrônica educativa para participar da votação simulada.

## 3. Lista de casos de uso

| **Código:**  UC01 | **Caso de uso:** Iniciar votação | **Objetivo:** Começar uma sessão de votação | **Responsável:** Mesário | 

| **Código:** UC02 | **Caso de uso:** Escolher candidato | **Objetivo:** Informar o número e conferir os dados do candidato desejado |  **Responsável:** Mesário | 

| **Código:** UC03 | **Caso de uso:** Corrigir escolha | **Objetivo:** Substituir uma escolha ainda não confirmada |  **Responsável:** Eleitor | 

| **Código:** UC04 | **Caso de uso:** Escolher voto em branco | **Objetivo:** Optar por não selecionar um candidato para o cargo atual |  **Responsável:** Eleitor | 

| **Código:** UC05 | **Caso de uso:** Confirmar voto | **Objetivo:** Registrar definitivamente a escolha realizada |  **Responsável:** Eleitor | 

| **Código:** UC06 | **Caso de uso:** Avançar para o próximo cargo | **Objetivo:** Continuar a votação após a confirmação do voto atual |  **Responsável:** Eleitor | 

| **Código:** UC07 | **Caso de uso:** Encerrar votação | **Objetivo:** Finalizar a sessão após a autorização do mesário |  **Responsável:** Mesário | 

| **Código:** UC08 |  **Caso de uso:** Autenticar mesário | **Objetivo:** Validar a senha para permitir o acesso a área administrativa |  **Responsável:** Mesário | 

| **Código:** UC09 |  **Caso de uso:** Consultar resultados da votação | **Objetivo:** Consultar a apuração inicial ou final, conforme a sessão e as permissões de acesso |  **Responsável:** Mesário | 

## 4. Descrição dos casos de uso

**UC01** — Iniciar votação

**Ator principal:** Mesário.

**Objetivo:** liberar a sessão geral para receber votos.

**Pré-condições:**

° A sessão de votação ainda não foi iniciada.

° O mesário possui autorização para acessar a área administrativa.

° Os resultados iniciais podem ser consultados.

**Fluxo principal:**

1. O mesário acessa a área administrativa.
2. O sistema solicita a autenticação por senha.
3. O mesário informa a senha.
4. O sistema valida a senha e permite o acesso autorizado.
5. O mesário consulta os resultados iniciais.
6. O sistema apresenta a quantidade inicial de votos e os percentuais dos candidatos.
7. O mesário verifica se não existem votos registrados e se os candidatos apresentam zero votos e zero por cento.
8. O mesário autoriza o início da votação.
9. O sistema altera o estado da sessão para em andamento e disponibiliza a urna para o primeiro eleitor.

**Fluxos alternativos:**

° **Senha inválida:** o sistema informa que a autenticação não foi realizada e não libera o acesso administrativo.

° **Resultados iniciais diferentes de zero:** o sistema não libera o início da votação até que a situação seja verificada e resolvida pela equipe autorizada.

**Pós-condição:** a sessão geral está em andamento e pode receber votos.

---

**UC02**  — Escolher candidato

**Ator principal:** Eleitor.

**Objetivo:** selecionar um candidato e consultar suas informações antes de registrar o voto.

**Pré-condições:**

° A sessão geral está em andamento.

° O eleitor está na etapa de votação correspondente ao cargo atual.

**Fluxo principal:**

1. O eleitor digita o número do candidato.

2. O sistema verifica se a quantidade de dígitos está correta e procura o número no cadastro de candidatos.

3. Quando encontra um candidato correspondente, o sistema apresenta seu nome, número e partido.

4. O eleitor confere os dados apresentados.

5. O eleitor decide se deseja corrigir ou confirmar a escolha.

**Fluxos alternativos:**

° **Número incompleto:** o sistema informa que a digitação está incompleta.

° **Candidato não encontrado:** o sistema apresenta uma mensagem informativa e permite que o eleitor corrija a escolha.

° **Escolha a ser corrigida:** o eleitor pode utilizar a função Corrige antes de confirmar o voto.

**Pós-condição:** a escolha está apresentada para conferência, mas ainda não foi registrada como voto confirmado.

---

**UC03**  — Corrigir escolha

**Ator principal:** Eleitor

**Objetivo:** permitir que o eleitor altere a escolha antes da confirmação definitiva.

**Pré-condições:**
° A sessão geral está em andamento.

° O voto do cargo atual ainda não foi confirmado.

**Fluxo principal:** 
1. O eleitor percebe que deseja alterar a escolha apresentada
2. O eleitor pressiona o botão Corrige
3. O sistema limpa a escolha atual
4. O eleitor informa um novo número ou seleciona outra opção disponível

**Fluxos alternativos:**

° **Nenhuma escolha digitada:** se não houver uma escolha digitada, o sistema segue o comportamento definido pela equipe para essa situação.

**Pós-condição:** a escolha anterior foi limpa e o eleitor pode realizar uma nova seleção

---

**UC04**  — Escolher voto em branco

**Ator principal:**  Eleitor

**Objetivo:**  selecionar a opção de voto em branco para o cargo atual

**Pré-condição:**  
° A sessão geral está em andamento.

° O voto do cargo atual ainda não foi confirmado.

**Fluxo principal:** 
1. O eleitor pressiona o botão Branco
2. O sistema apresenta a indicação de voto em branco
3. O eleitor confere a opção selecionada
4. O eleitor pressiona o botão Confirma para registrar o voto em branco

**Pós-condição:**  o voto em branco é registrado após a confirmação válida

---

**UC05**  — Escolher voto em branco

**Ator principal:**  Eleitor

**Objetivo:**  selecionar a opção de voto em branco para o cargo atual

**Pré-condição:**  a votação deve estar em andamento e o voto atual ainda não pode ter sido confirmado

**Fluxo principal:** 
1. O eleitor pressiona o botão Branco
2. O sistema apresenta a indicação de voto em branco
3. O eleitor confere a opção selecionada
4. O eleitor decide se deseja confirmar ou corrigir a escolha

**Pós-condição:**  a opção de voto em branco está selecionada, aguardando confirmação

---

**UC06** — Confirmar voto

**Ator principal:** Eleitor

**Objetivo:** registrar definitivamente a escolha realizada para o cargo atual

**Pré-condição:** deve existir uma escolha que possa ser confirmada, seja um candidato identificado ou a opção de voto em branco

**Fluxo principal:**
1. O eleitor confere a escolha apresentada na tela
2. O eleitor pressiona o botão Confirma
3. O sistema valida a escolha
4. O sistema registra o voto
5. O sistema impede que o eleitor altere o voto confirmado

**Fluxo alternativo**

° Se a escolha não atender às condições de confirmação, o sistema apresenta uma mensagem apropriada e não registra o voto como confirmado

**Pós-condição:** o voto foi registrado e não pode ser alterado pelo eleitor

---

**UC07** — Avançar para o próximo cargo

**Ator principal:** Eleitor

**Objetivo:** permitir a continuidade da votação após a confirmação do voto para Deputado Federal

**Pré-condição:** o voto para Deputado Federal deve ter sido confirmado

**Fluxo principal:**
1. O sistema conclui o registro do primeiro voto
2. O sistema apresenta a tela de votação para Presidente
3. O eleitor pode iniciar a escolha para o segundo cargo

**Pós-condição:** a votação está na etapa de escolha para Presidente.

---

**UC08** — Encerrar votação

**Ator principal:** Eleitor

**Objetivo:** finalizar a sessão após a conclusão das escolhas previstas

**Pré-condição:** o eleitor deve estar na etapa de votação para Presidente

**Fluxo principal:**
1. O eleitor escolhe e confere sua opção para Presidente
2. O eleitor pressiona Confirma
3. O sistema valida e registra o voto
4. O sistema finaliza a sessão de votação
5. O sistema informa que a votação foi concluída

**Pós-condição:** a sessão de votação foi encerrada e os votos confirmados foram registrados conforme as regras do sistema

---

## 5. Relação com os requisitos:

Os casos de uso deste documento complementam os requisitos registrados em **requisitos.md**. Enquanto os requisitos especificam as funcionalidades e regras que o sistema deverá atender, os casos de uso descrevem como o eleitor interage com a urna para realizar a votação.

---

## 6. Histórico de alterações:
Versão 1.0: criação inicial da documentação de casos de uso da Urna Cidadã.

---

## 7. Diagrama de casos de uso:

![Diagrama de casos de uso da Urna Cidadã](<Diagrama casos-de-uso.png>)






