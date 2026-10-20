# Requisitos e Regras da Urna Cidadã
## 1. Descrição do projeto
O projeto Urna Cidadã consiste no desenvolvimento de uma urna eletrônica educativa para simular o processo de votação. A proposta é apresentar aos estudantes o funcionamento básico de uma votação eletrônica, promovendo a educação cidadã e a conscientização sobre desinformação em períodos eleitorais.

O sistema será desenvolvido em Java, utilizando Java Swing para a interface gráfica e Firebase Cloud Firestore para o armazenamento dos dados, conforme o planejamento do projeto.

## 2. Cargos disponíveis e ordem de votação
A votação será realizada em duas etapas, seguindo esta ordem:

**1. Deputado Federal:** o eleitor deverá digitar um número de quatro dígitos.

**2. Presidente:** o eleitor deverá digitar um número de dois dígitos.

Cada cargo terá três candidatos fictícios, totalizando seis candidatos na simulação.

Os candidatos e partidos serão fictícios, e a definição dos nomes, números e partidos será realizada em conjunto pela equipe do projeto.

## 3. Requisitos funcionais: 
Os requisitos funcionais descrevem as ações que o sistema deverá permitir.

**RF01 — Iniciar votação:** permitir o início de uma sessão de votação por um mesário autorizado.

**RF02 — Exibir cargo:** mostrar na tela o cargo para o qual o eleitor deverá votar.

**RF03 — Digitar número:** permitir que o eleitor informe o número do candidato por meio do teclado da urna.

**RF04 — Identificar candidato:** apresentar o nome, o número e o partido do candidato quando o número digitado corresponder a um candidato cadastrado.

**RF05 — Corrigir escolha:** permitir que o eleitor apague a escolha atual antes da confirmação e digite outro número.

**RF06 — Voto em branco:** permitir que o eleitor selecione a opção de voto em branco.

**RF07 — Confirmar voto:** registrar a escolha após o eleitor pressionar o botão “Confirma”, respeitando as validações do sistema.

**RF08 — Número não encontrado:** exibir uma mensagem informando que o candidato não foi encontrado quando o número digitado não corresponder a um candidato cadastrado.

**RF09 — Número incompleto:** exibir uma mensagem informando que o número digitado está incompleto quando não possuir a quantidade de dígitos exigida para o cargo.

**RF10 — Avançar votação:** após a confirmação do voto para Deputado Federal, avançar para a votação de Presidente.

**RF11 — Encerrar votação:** após a confirmação do voto para Presidente, finalizar a sessão de votação.

**RF12 — Armazenar votos:** armazenar os votos no Firebase Cloud Firestore, conforme a estrutura de dados definida pela equipe.

**RF13 — Apresentar resultados:** permitir a apuração e a apresentação dos resultados da votação, conforme as regras estabelecidas para a simulação.

**RF14 — Autenticação dos mesários:** permitir que os mesários acessem a área administrativa mediante autenticação por senha.

**RF15 — Conferir contabilização inicial:** exibir aos mesários a quantidade de votos registrados e as porcentagens dos candidatos antes do início da votação, permitindo verificar se todos os valores estão zerados.

**RF16 — Liberar votação:** permitir que um mesário autorizado inicie a sessão após conferir a contabilização inicial.

**RF17 — Encerrar sessão administrativa:** permitir que um mesário autorizado encerre a sessão geral de votação mediante autenticação por senha.

**RF18 — Consultar apuração final:** permitir que os mesários consultem a quantidade de votos e as porcentagens dos candidatos após o encerramento da sessão.

**RF19 — Restringir acesso administrativo:** impedir que os eleitores acessem a área administrativa, a senha dos mesários e os resultados da votação.

**RF20 — Controlar estado da sessão:** controlar os estados da votação, distinguindo sessão não iniciada, sessão em andamento e sessão encerrada.

**RF21 — Apresentar vídeos dos candidatos:** permitir a exibição dos vídeos dos candidatos fictícios durante a etapa educativa de campanha, apresentando seus nomes, números, partidos e propostas.

**RF22 — Disponibilizar números dos candidatos:** permitir que os alunos consultem os números dos candidatos durante a campanha e os anotem para utilizá-los na simulação da votação.


## 4. Regras de negócio:
As regras de negócio determinam como a votação deverá funcionar.

**RN01 — Ordem de votação:** o eleitor deverá votar primeiro para Deputado Federal e depois para Presidente.

**RN02 — Número de Deputado Federal:** o número deverá conter quatro dígitos.

**RN03 — Número de Presidente:** o número deverá conter dois dígitos.

**RN04 — Correção:** o botão “Corrige” deverá limpar a escolha atual antes da confirmação, permitindo uma nova digitação.

**RN05 — Voto em branco:** ao selecionar “Branco”, o sistema deverá indicar a opção de voto em branco. O voto somente será registrado após o eleitor pressionar “Confirma”.

**RN06 — Confirmação:** o voto deverá ser registrado após a confirmação, desde que as validações necessárias sejam atendidas.

**RN07 — Avanço de etapa:** após a confirmação do voto para Deputado Federal, o sistema deverá apresentar a etapa de votação para Presidente.

**RN08 — Encerramento:** após a confirmação do voto para Presidente, o sistema deverá encerrar a sessão de votação.

**RN09 — Identificação do candidato:** quando houver correspondência com um candidato cadastrado, o sistema deverá exibir seu nome, número e partido.

**RN10 — Candidato não encontrado:** quando o número digitado não corresponder a um candidato cadastrado, o sistema deverá informar que o candidato não foi encontrado, sem classificar automaticamente a escolha como voto nulo.

**RN11 — Número incompleto:** quando o número digitado não possuir a quantidade de dígitos exigida para o cargo, o sistema deverá informar que a digitação está incompleta.

**RN12 — Candidatos e partidos fictícios:** a simulação utilizará candidatos e partidos fictícios, sem vínculo com candidatos ou partidos políticos reais. A equipe definirá em conjunto os nomes, números e partidos.

**RN13 — Imutabilidade do voto confirmado:** depois de confirmado, o voto não poderá ser alterado pelo eleitor. O sistema deverá avançar para o próximo cargo ou encerrar a sessão, conforme a etapa da votação.

**RN14 — Identificação exclusiva:** cada candidato deverá possuir um número próprio e exclusivo dentro do respectivo cargo, evitando ambiguidades na identificação durante a votação.

**RN15 — Acesso restrito aos mesários:** somente os integrantes autorizados da equipe, que exercerão a função de mesários, poderão acessar a área administrativa mediante senha.

**RN16 — Conferência inicial obrigatória:** antes de liberar a votação, os mesários deverão verificar se não existem votos registrados na sessão e se todos os candidatos apresentam zero votos e 0% na contabilização inicial.

**RN17 — Liberação da sessão:** a votação somente poderá começar após a autenticação de um mesário e a conferência da contabilização inicial.

**RN18 — Sigilo da senha administrativa:** a senha de acesso à área administrativa não deverá ser exibida na tela de votação nem disponibilizada aos eleitores.

**RN19 — Restrição de resultados durante a votação:** os eleitores não poderão consultar a contabilização dos votos ou as porcentagens dos candidatos enquanto a sessão estiver em andamento.

**RN20 — Encerramento por mesário:** o encerramento da sessão geral deverá ser realizado por um mesário autorizado, mediante autenticação por senha.

**RN21 — Consulta dos resultados após o encerramento:** a apuração final deverá ficar disponível apenas na área administrativa, após o encerramento da sessão de votação.

**RN22 — Integridade dos resultados:** a contabilização deverá considerar os votos efetivamente confirmados e registrados, sem contabilizar escolhas ainda não confirmadas.

**RN23 — Controle dos estados da sessão:** a urna deverá impedir que os eleitores votem antes da liberação da sessão ou após seu encerramento.

**RN24 — Proteção da área administrativa:** o acesso aos resultados e às funcionalidades administrativas deverão ser protegidos pela lógica do sistema e pelas permissões de acesso aos dados, não apenas pela ocultação de telas ou botões.

**RN25 — Campanha educativa:** antes do início da votação, os alunos poderão assistir aos vídeos dos candidatos fictícios para conhecer seus nomes, números, partidos e propostas.

**RN26 — Igualdade de apresentação:** os vídeos deverão seguir critérios previamente definidos pela equipe, como duração aproximada, formato e tempo de apresentação.

**RN27 — Anotação dos números:** os alunos poderão anotar os números dos candidatos durante a campanha e consultar suas próprias anotações durante a simulação da votação.

**RN28 — Separação entre campanha e votação:** a apresentação dos vídeos e a anotação dos números deverão ocorrer antes da abertura da sessão de votação pelos mesários.

**RN29 — Caráter fictício da campanha:** os vídeos, nomes, partidos e propostas deverão ser elaborados para fins educativos e identificados como parte de uma simulação, sem representar candidaturas ou partidos políticos reais.

## 5. Fluxo básico da votação:
1. O eleitor inicia a sessão de votação.

2. O sistema apresenta o cargo de Deputado Federal.

3. O eleitor digita o número do candidato ou seleciona a opção de voto em branco.

4. O sistema verifica a escolha e apresenta as informações disponíveis ou uma mensagem apropriada.

5. Antes da confirmação, o eleitor poderá corrigir a escolha.

6. O eleitor pressiona “Confirma” para registrar o voto.

7. O sistema avança para o cargo de Presidente.

8. O eleitor realiza a segunda votação seguindo as mesmas regras de digitação, correção e confirmação.

9. Após a confirmação do segundo voto, o sistema encerra a sessão.

10. Os votos são armazenados para a posterior apuração, conforme a implementação do sistema.

## 6. Requisitos não funcionais:
Os requisitos não funcionais descrevem características de qualidade e segurança esperadas para o sistema.

**RNFs01 — Usabilidade:** a interface deverá ser simples e compreensível para os estudantes participantes da atividade.

**RNFs02 — Consistência:** o sistema deverá manter o mesmo padrão de funcionamento para os dois cargos, respeitando as diferenças de quantidade de dígitos.

**RNFs03 — Integridade dos votos:** cada voto confirmado deverá ser registrado uma única vez.

**RNFs04 — Confiabilidade:** o sistema deverá validar as informações antes de registrar cada voto.

**RNFs05 — Manutenibilidade:** o código deverá ser organizado para facilitar futuras alterações.

**RNFs06 — Segurança:** as credenciais e configurações sensíveis do Firebase deverão ser protegidas conforme as boas práticas aplicáveis.

**RNFs07 — Clareza das mensagens:** o sistema deverá apresentar mensagens compreensíveis para orientar o eleitor durante a votação.

## 7. Pendências para decisão da equipe:
Os itens abaixo ainda precisam ser discutidos e definidos em grupo, antes de serem considerados regras aprovadas.

**PEND01 — Cadastro dos candidatos:** definir os três candidatos fictícios para Deputado Federal e os três para Presidente.

**PEND02 — Dados dos candidatos:** escolher os nomes, números e partidos fictícios de cada candidato, garantindo que os números sejam exclusivos dentro de cada cargo.

**PEND03 — Voto nulo:** definir como o sistema identificará e registrará um voto nulo, caso essa funcionalidade faça parte do escopo.

**PEND04 — Número não encontrado:** definir o que acontecerá após a mensagem de candidato não encontrado e como o eleitor poderá prosseguir.

**PEND05 — Correção sem digitação:** definir o comportamento do botão “Corrige” quando não houver número digitado.

**PEND06 — Mensagens da interface:** definir as mensagens exibidas em cada situação da votação.

**PEND07 — Armazenamento:** definir a estrutura dos registros no Firestore e as informações que serão armazenadas.

**PEND08 — Apuração:** definir como os votos serão contabilizados e apresentados ao final da atividade.

**PEND09 — Acesso aos resultados:** definir quem poderá consultar os resultados da votação.

## 8. Critérios iniciais de aceitação
O sistema será considerado de acordo com os requisitos básicos quando:

° Apresentar os cargos na ordem definida.

° Respeitar a quantidade de dígitos de cada cargo.

° Exibir as informações do candidato quando o número for válido e estiver cadastrado.

° Permitir corrigir a escolha antes da confirmação.

° Permitir selecionar e confirmar um voto em branco.

° Apresentar mensagens para números incompletos ou não encontrados.

° Impedir a alteração de um voto após sua confirmação.

° Avançar para Presidente após a confirmação do voto para Deputado Federal.

° Encerrar a sessão após a confirmação do voto para Presidente.

° Registrar os votos conforme as regras e a estrutura de armazenamento aprovadas pela equipe.

## 9. Histórico de alterações
Versão 1.0: criação inicial da documentação de requisitos, com a definição dos cargos, da ordem de votação, das regras básicas e das pendências para discussão da equipe.

Versão 1.1: Atualização dos requisitos para incluir a autenticação dos mesários, a verificação dos resultados iniciais, o controle de abertura e encerramento da sessão geral e a consulta aos resultados finais. Inclusão das regras de acesso à área administrativa e registro da campanha educativa com vídeos dos candidatos como funcionalidade ainda pendente de definição.
