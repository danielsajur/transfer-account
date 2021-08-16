
## Métodos HTTP e exemplos

### Cadastramento do agendamento da transferência
**POST ->** http://localhost:8080/transfers

### Request
```
{
	"originAccount" : "374632",
	"destinationAccount" : "279872",
	"transferDate" : "2021-09-14",
	"value" : 100001
}
```

### Consultar todos os agendamentos
**GET ALL ->** http://localhost:8080/transfers


### Consultar todos os agendamentos de um usuário através da sua conta origem
**GET ->** http://localhost:8080/transfers/{originAccount}

### Response
```
{
    "timestamp": "2021-08-60 23:48:36",
    "data": [
        {
            "id": "b466e74e-a195-4f50-8822-d93342877cdc",
            "originAccount": "374632",
            "destinationAccount": "279872",
            "transferDate": "2021-12-25",
            "value": 100001,
            "fare": 2000.02,
            "booking": "2021-08-15",
            "type": "C"
        },
        {
            "id": "de9d7db1-4aab-4f7e-9409-a1d82c8843d5",
            "originAccount": "374632",
            "destinationAccount": "279872",
            "transferDate": "2021-10-25",
            "value": 100001,
            "fare": 2000.02,
            "booking": "2021-08-15",
            "type": "C"
        },
        {
            "id": "cdf88fee-cbe0-4ccb-a54c-d66bfdaeb0bb",
            "originAccount": "374632",
            "destinationAccount": "279872",
            "transferDate": "2021-09-25",
            "value": 100001,
            "fare": 2000.02,
            "booking": "2021-08-15",
            "type": "C"
        },
        {
            "id": "b9a38a8a-5296-413c-a18e-b16fe28786a2",
            "originAccount": "374632",
            "destinationAccount": "279872",
            "transferDate": "2021-09-24",
            "value": 100001,
            "fare": 4000.04,
            "booking": "2021-08-15",
            "type": "C"
        },
        {
            "id": "e3010ae9-1839-4343-a134-0bfe8ca3cb7d",
            "originAccount": "374632",
            "destinationAccount": "279872",
            "transferDate": "2021-09-14",
            "value": 100001,
            "fare": 6000.06,
            "booking": "2021-08-15",
            "type": "C"
        }
    ]
}
```

* Projeto desenvolvido com Springboot, sem presistência em banco de dados. Não requer configurações adicionais para inicialização.
* Testes integrados escritos com suporte ao Springboot.
* A lógica de negócio está encapsulada na classe **TransferBuilder** no pacote **br.com.cvc.banktransfer.domain.entity** para manter o domínio da aplicação desacoplado das tecnologias e configurações do sistema usando princípios do DDD.