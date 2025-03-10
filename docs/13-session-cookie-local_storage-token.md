# Session, Cookie, Local Storage, and Token: Quick Reference

| Concept          | Storage Location        | Lifecycle                            | Scope           | Security Considerations                                |
|------------------|-------------------------|--------------------------------------|-----------------|--------------------------------------------------------|
| **Session**      | Server-side (Memory/DB) | Until logout or timeout              | Server-wide     | Secure, but requires session management               |
| **Cookie**       | Browser (Client-side)   | Configurable (Expires/Session)       | Domain-wide     | Can be stolen (XSS), should use `HttpOnly` & `Secure` flags |
| **Local Storage**| Browser (Client-side)   | Persistent (Until cleared)           | Per domain      | Vulnerable to XSS attacks                             |
| **Token (JWT, OAuth)** | Client-side (Stored in Cookie/LocalStorage) | Usually short-lived + Refresh Token | Stateless (No session) | Should be encrypted, signed, and refreshed            |

## Common Confusions
- **Session vs Token**: Session is stored **server-side**, while tokens are **stateless and stored client-side**.
- **Cookie vs Local Storage**: Cookies support **automatic transmission** with requests; Local Storage is **manual**.
- **Session vs Cookie**: Session relies on **server memory**, whereas a cookie stores small data in **client storage**.