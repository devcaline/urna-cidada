# Casos de Uso — Urna Cidadã
## 1. Objetivo
Este documento descreve como o eleitor interage com o sistema Urna Cidadã durante uma votação simulada. Os casos de uso apresentam os objetivos do usuário, o fluxo principal de interação e as situações alternativas que podem ocorrer durante a votação.
As regras detalhadas e os requisitos funcionais do sistema estão documentados no arquivo requisitos.md.

## 2. Ator principal
Eleitor: pessoa que utiliza a urna eletrônica educativa para participar da votação simulada.

## 3. Lista de casos de uso

| **Código:**  UC01 | **Caso de uso:** Iniciar votação | **Objetivo:** Começar uma sessão de votação |

| **Código:** UC02 | **Caso de uso:** Escolher candidato | **Objetivo:** Informar o número e conferir os dados do candidato desejado |

| **Código:** UC03 | **Caso de uso:** Corrigir escolha | **Objetivo:** Substituir uma escolha ainda não confirmada |

| **Código:** UC04 | **Caso de uso:** Escolher voto em branco | **Objetivo:** Optar por não selecionar um candidato para o cargo atual |

| **Código:** UC05 | **Caso de uso:** Confirmar voto | **Objetivo:** Registrar definitivamente a escolha realizada |

| **Código:** UC06 | **Caso de uso:** Avançar para o próximo cargo | **Objetivo:** Continuar a votação após a confirmação do voto atual |

| **Código:** UC07 | **Caso de uso:** Encerrar votação | **Objetivo:** Finalizar a sessão após a última confirmação |

## 4. Descrição dos casos de uso

**UC01**  — Iniciar votação

**Ator principal:**  Eleitor

**Objetivo:**  iniciar a participação na votação simulada.

**Pré-condição:**  a urna deve estar disponível para iniciar uma sessão.

**Fluxo principal:** 
1. O eleitor inicia a votação.
2. O sistema apresenta a tela correspondente ao cargo de Deputado Federal.
3. O eleitor visualiza as informações necessárias para começar a escolha.

**Pós-condição:**  a votação está iniciada e pronta para receber a primeira escolha.

**UC02**  — Escolher 

**Ator principal:** Eleitor

**Objetivo:** selecionar um candidato e conferir suas informações antes de registrar o voto
**Pré-condição:** a votação deve estar iniciada e um cargo deve estar sendo apresentado
**Fluxo principal:** 
1. O eleitor digita o número do candidato
2. O sistema verifica se a quantidade de dígitos está correta e procura o número no cadastro de candidatos
3. Quando encontra um candidato correspondente, o sistema apresenta seu nome, número e partido
4. O eleitor confere os dados apresentados
5. O eleitor decide se deseja corrigir a escolha ou confirmá-la

**Fluxos alternativos:**
° **Número incompleto:** o sistema informa que a digitação está incompleta
° **Candidato não encontrado:** o sistema apresenta uma mensagem informativa e aguarda o procedimento definido para essa situação
° **Escolha incorreta:** o eleitor pode utilizar a função de correção antes de confirmar o 

**Pós-condição:** a escolha está apresentada para conferência, mas ainda não foi registrada como voto confirmado




















