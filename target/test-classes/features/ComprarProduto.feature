#language: pt

Funcionalidade: Comprar Produto
Esquema do Cenario: Compra com sucesso
    Dado que acesso o My Demo App
    Entao verifico o logo e nome da secao
    E localizo o <produto> que na <rolagem> esta na posicao <num_produto> por <preco>
    Quando clico na imagem do <num_produto>
    Entao na tela do produto visualizo o <produto> e o <preco> 
    Quando arrasto para cima e clico Add Cart
    Entao na tela do carrinho visualizo o <produto> <preco> e <quantidade>
    Exemplos:
    |produto                |preco    |rolagem|num_produto|quantidade|
    |"Sauce Labs Backpack"  |"$ 29.99"|0      |1          |1         |
    #|"Sauce Labs Bike Light"|"$ 9.99" |2      |3          |1         |


