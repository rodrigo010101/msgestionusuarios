# anotaciones importantes

<Rol> = si quiero devolver el objeto, con respuesta Ok 200.(cliente necesita los datos eliminados)
<Void>= si no quiero devolver el objeto como respuesta o sin cuerpo No_CONTENT 204.(operacion silenciosa , solo confirmacion)

# Verbos HTTP

GET = solicitud de datos,recupera datos de un recurso(solo lectura).
POST= envia datos solo para crear un nuevo recurso.
PUT = Actualiza un recurso existente o crearlo si no existe a diferencia de PATCH solo modifica algunos campos
Delete= Elimina un recurso especifico.

# @RestController

Indica que esta clase sera la controladora de la clase , donde cada metodo devolvera un objeto de dominio en lugar de una vista.

# implementacion de restriccion para no manipular el id
