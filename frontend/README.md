# Frontend (Angular)

Proyecto base Angular (standalone) preparado para conectarse al backend de Ticketing SaaS.

## Configuración API
- URL base en `src/environments/environment*.ts`.
- Por defecto: `http://localhost:8080/api/v1`.

## Flujo implementado
- Pantalla inicial con formulario de login.
- Llamada a `POST /api/v1/auth/login`.
- Guardado de `accessToken` en `localStorage`.
- Interceptor HTTP para enviar `Authorization: Bearer <token>` en llamadas futuras.

## Comandos
```bash
npm install
npm start
```

Si quieres usar proxy local en desarrollo:
```bash
npm start -- --proxy-config proxy.conf.json
```
