# Frontend (Angular)

Proyecto base Angular (standalone) preparado para conectarse al backend de Ticketing SaaS.

## Configuración API
- URL base en `src/environments/environment*.ts`.
- Por defecto: `http://localhost:8080/api/v1`.

## Flujo implementado
- Pantalla de login con llamada a `POST /api/v1/auth/login`.
- Guardado de `accessToken` en `localStorage`.
- Interceptor HTTP para enviar `Authorization: Bearer <token>` automáticamente.
- Formulario para creación de tickets con `POST /api/v1/tickets`.
- Listado básico de tickets (`GET /api/v1/tickets`) para validar la creación.

## Comandos
```bash
npm install
npm start
```

Si quieres usar proxy local en desarrollo:
```bash
npm start -- --proxy-config proxy.conf.json
```
