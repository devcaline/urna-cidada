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

**UC05**  — Confirmar voto

**Ator principal:**  Eleitor

**Objetivo:** registrar definitivamente a escolha realizada para o cargo atual

**Pré-condição:** 
° A sessão geral está em andamento

° Existe uma escolha válida para confirmação, seja um candidato identificado ou a opção de voto em branco

**Fluxo principal:** 
1. O eleitor confere a escolha apresentada na tela
2. O eleitor confere a escolha apresentada na tela
3. O sistema valida a escolha
4. O sistema registra o voto
5. O sistema impede que o eleitor altere o voto confirmado

**Fluxo alternativo:**

° **Escolha inválida ou incompleta:** o sistema apresenta uma mensagem informativa e não registra o voto como confirmado

**Pós-condição:** o voto do cargo atual foi registrado definitivamente e não pode ser alterado pelo eleitor

---

**UC06** — Avançar para o próximo cargo

**Ator principal:** Eleitor

**Objetivo:** permitir a continuidade da votação após a confirmação do voto para Deputado Federal

**Pré-condições:** 
° A sessão geral está em andamento

° O voto para Deputado Federal foi confirmado

**Fluxo principal:**
1. O sistema conclui o registro do voto para Deputado Federal
2. O sistema apresenta a tela de votação para Presidente
3. O eleitor inicia a escolha para o segundo cargo

**Pós-condição:** o eleitor está na etapa de votação para Presidente

---

**UC07** — Encerrar votação

**Ator principal:** Mesário

**Objetivo:** encerrar a sessão geral de votação e liberar a consulta aos resultados finais

**Pré-condições:** 
° A sessão geral está em andamento

° O mesário possui autorização para acessar a área administrativa

**Fluxo principal:**
1. O mesário acessa a área administrativa
2. O sistema solicita autenticação por senha
3. O mesário informa a senha
4. O sistema valida a senha e permite o acesso autorizado
5. O mesário solicita o encerramento da sessão geral
6. O sistema altera o estado da sessão para encerrada
7. O sistema impede o registro de novos votos
8. O sistema libera a consulta aos resultados finais para o mesário autorizado

**Fluxos alternativos:**

° **Senha inválida:** o sistema não permite o acesso administrativo nem o encerramento da sessão

° **Sessão já encerrada:** o sistema informa que a votação já foi finalizada

**Pós-condição:** a sessão geral está encerrada, não aceita novos votos e permite a consulta autorizada à apuração final

---

**UC08** — Autenticar mesário

**Ator principal:** Mesário

**Objetivo:** validar a senha para permitir o acesso à área administrativa

**Pré-condição:** o mesário está na tela de acesso administrativo

**Fluxo principal:**
1. O sistema solicita a senha
2. O mesário informa a senha
3. O sistema verifica se a senha é válida
4. O sistema libera o acesso administrativo autorizado

**Fluxo alternativo:**

° **Senha inválida:** o sistema informa que a senha está incorreta e não permite o acesso administrativo

**Pós-condição:** o mesário autenticado pode acessar as funcionalidades administrativas permitidas para o estado atual da sessão

---

**UC09** — Consultar resultados da votação

**Ator principal:** Mesário

**Objetivo:** consultar a quantidade de votos e os percentuais dos candidatos conforme o estado da sessão

**Pré-condições:** 

° O mesário está autenticado

° A consulta está autorizada para o estado atual da sessão

**Fluxo principal:**
1. O mesário acessa a área de resultados
2. O sistema verifica o estado da sessão
3. Se a sessão ainda não foi iniciada, o sistema apresenta os resultados iniciais para verificação
4. Se a sessão foi encerrada, o sistema apresenta a apuração final
5. O sistema exibe a quantidade de votos e os percentuais calculados a partir dos dados registrados

**Fluxos alternativos:**

° **Sessão em andamento:** o sistema não disponibiliza os resultados aos eleitores nem a usuários não autorizados

° **Acesso não autorizado:** o sistema impede a consulta

° **Nenhum voto registrado:** o sistema apresenta zero votos e zero por cento, sem realizar divisão por zero

**Pós-condição:** os resultados autorizados são apresentados ao mesário


## 5. Relação com os requisitos:

Os casos de uso deste documento complementam os requisitos registrados em **requisitos.md**. Enquanto os requisitos especificam as funcionalidades e regras que o sistema deverá atender, os casos de uso descrevem como o eleitor interage com a urna para realizar a votação.

---

## 6. Histórico de alterações:
Versão 1.0: criação inicial da documentação de casos de uso da Urna Cidadã

Versão 1.1: atualização dos casos de uso para incluir autenticação do mesário, verificação inicial dos resultados, consulta à apuração e encerramento da sessão geral

---

## 7. Diagrama de casos de uso:

![Diagrama de casos de uso da Urna Cidadã](<Diagrama casos-de-uso.png>)






