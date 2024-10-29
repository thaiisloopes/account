## Como garantir que apenas uma transação por conta seja processada em um determinado momento?

### Alternativa escolhida
Considerando o cenário de transações que alteram e obtém o saldo disponível em conta,
entende-se que deve-se garantir a consistência do dado lido para que haja a devida
consistência no dado escrito. Com isso, foi escolhida a abordagem de utilizar o lock 
pessimista.

### Funcionamento
O JPA fornece a solução para este caso através do LockModeType.PESSIMISTIC_WRITE. A partir desta solução,
o JPA garante que outras transações não leiam, atualizem ou excluam o dado, enquanto uma transação
já está em andamento.

### Outras informações
Considerando o tempo de processamento das transações de menos de 100ms, para validação, é possível 
realizar testes de carga.
Caso haja necessidade, implementar melhorias, como otimização de query. 

### Referência
- [PESSIMISTIC_WRITE](https://www.baeldung.com/jpa-pessimistic-locking#2-pessimisticwrite)
