curl -i -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' -d '[              
  {
    "stockId": "SKU45825",
    "amount": 10
  }, 
  { 
    "stockId" : "SKU32987", 
    "amount" : 10 
  }
]' 'http://localhost:8282/checkout'

