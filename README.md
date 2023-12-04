# TPO_OOP
Alumnos: Tanus Linares Santino, Leguizamon Federico

El proyecto trata de una API REST que permite conseguir las coordenadas de un exoplaneta mediante un algoritmo de trilateracion.
Tambien pasa a limpio un mensaje encriptado a partir de los mensajes que poseen cada satelite/telescopio cuyo contenido se encuentra incompleto o desfasado.
El proyecto cuenta con 4 funciones principales:
-getEncryptedMessage, la cual permite obtener el mensaje encriptado en base a los mensajes enviados por la request.
-editSecretMessage, la cual permite editar el mensaje secreto mediante un archivo de configuracion.
-getPlanetCoordinates, la cual permite obtener las coordenadas del exoplaneta a traves de las coordenadas y distancias recibidas por parametros.
-getRequest, la cual recibe una request general y devuelve todos los datos del exoplaneta ademas del mensaje encriptado.

En el caso del getRequest, los datos de los satelites estan hardcodeados por lo que siempre seran los mismos, las unicas diferencias son las distancias y los mensajes secretos,
los cuales son datos que se reciben por la request.
