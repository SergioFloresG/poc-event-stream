# Documentación del Endpoint SSE

## Descripción

Este documento proporciona detalles sobre cómo consumir el endpoint que provee eventos del
servidor (SSE) desde el controlador `SseController`.

## Endpoint: `/stream-sse`

### Descripción

Este endpoint proporciona un flujo de eventos del servidor (SSE). Los clientes pueden suscribirse a
este endpoint para recibir una secuencia continua de mensajes.

### GET /stream-sse

### Encabezados Requeridos:

- `Accept: text/event-stream`

### Ejemplo de cURL

```sh
curl -N -H "Accept: text/event-stream" http://localhost:8080/stream-sse
```

### Respuesta

La respuesta será una serie de eventos del servidor (`ServerSentEvent`) con el tipo
`text/event-stream`. Cada evento estará compuesto por:

1. **data**: La carga útil del mensaje (en este caso, fragmentos de texto de "Lorem Ipsum").
2. **id**: El identificador secuencial del evento.
3. **event**: El tipo de evento, que en este caso, el último mensaje enviado tendrá el valor
   `"END"`.

#### Ejemplo de Respuesta (Formato EventStream)

```plaintext
id:1
event:MESSAGE
data:{"data":"Enim ","chunk":{"id":1,"event":"MESSAGE","timestamp":"2024-09-16T00:19:53.7882245"}}

id:2
event:MESSAGE
data:{"data":"eum voluptatem ","chunk":{"id":2,"event":"MESSAGE","timestamp":"2024-09-16T00:19:54.3033026"}}

id:3
event:MESSAGE
data:{"data":"occaecati doloribus ","chunk":{"id":3,"event":"MESSAGE","timestamp":"2024-09-16T00:19:54.8186806"}}

id:4
event:MESSAGE
data:{"data":"at quam ","chunk":{"id":4,"event":"MESSAGE","timestamp":"2024-09-16T00:19:55.3331746"}}

id: 5
event: END
```

### Detalles de la Implementación

El controlador `SseController` inyecta `LoremUseCase` para obtener fragmentos de "Lorem Ipsum".
Estos fragmentos se procesan y envían como eventos individuales de SSE con el tipo de evento
`MESSAGE`. El último evento incluirá el tipo de evento `END`.

### Requisitos del Cliente

Para recibir y procesar eventos SSE, el cliente debe soportar el formato SSE. A continuación, se
muestra un ejemplo de cómo puede implementarse en diferentes entornos.

### Ejemplo en JavaScript

```javascript
const eventSource = new EventSource("http://localhost:8080/stream-sse");

eventSource.onmessage = function (event) {
  console.log("New message:", event.data);
};

eventSource.addEventListener("END", function (event) {
  console.log("Stream ended:", event.data);
  eventSource.close();
});

eventSource.onerror = function (event) {
  console.error("Error occurred:", event);
};
```

Este ejemplo crea una conexión a la URL `http://localhost:8080/stream-sse` y maneja los mensajes
entrantes, mostrando cada uno en la consola. Cuando llega un evento con el tipo `END`, cierra la
conexión.
