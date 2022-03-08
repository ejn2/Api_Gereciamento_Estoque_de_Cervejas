# Gerenciamento estoque de cervejas. 🍺
<p> É uma api para estocar e gerenciar cervejas, desenvolvida em spring boot.</p>

## Rotas

<p>Use a url abaixo para acessar as rotas.</P>
<code>/api/v1/beerstock</code>

### Exemplo:
<code>http://localhost:8080/api/v1/beerstock</code>

<br>

<table border="1">
    <th>Endpoint</th><th>Método</th><th>Ação</th>
    <tr>
        <td>/api/v1/beerstock</td><td>GET</td><td>É retornada uma lista de cervejas.
</td>
    </tr>
        <tr>
        <td>/api/v1/beerstock/{ id }</td><td>GET</td><td>É      retornada uma cerveja específica do ID informado.
</td>
    </tr>
        <tr>
        <td>/api/v1/beerstock</td><td>POST</td><td>Faz o registro de uma nova cerveja.
</td>
    </tr>
    </td>
    </tr>
        <tr>
        <td>/api/v1/beerstock/{ id }</td><td>PUT</td><td>Atualize uma cerveja específica do ID informado.
</td>
</td>
    </tr>
    </td>
    </tr>
        <tr>
        <td>/api/v1/beerstock/{ id }</td><td>DELETE</td><td>Faz a exclusão de uma cerveja específica do ID informado.

</td>
    </tr>
</table>

<br>

## Dados de entrada.

### Registar nova cerveja. <br>
<p>Para registar uma nova cerveja, você precisa passar os seguintes dados de entrada. <code>name</code>, que será o nome para a nova cerveja, <code>brand</code>, que será a marca, <code>quantity</code>, que será a quantidade.
</p>

⚠️ Isso serve também para o update.


<p> ⚠️ Todos os dados de entrada devem ser passados no formato JSON. </p>

### Exemplo:

~~~JSON
{
  "name": "Cerveja-01",
  "brand": "Marca-1",
  "quantity": 15
}
~~~