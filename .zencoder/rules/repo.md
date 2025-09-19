# Repo Info

- Name: Todo App (frontend React, backend Spring Boot)
- Backend: d:\\For Clients\\new\\todo-app-backend
- Frontend: d:\\For Clients\\new\\todo-frontend
- CI: .github/workflows/ci.yml
- Perf: performance/k6
- Security: security/

Build/Run:
- Backend tests: mvn -f todo-app-backend/pom.xml -DskipITs test
- Frontend tests: npm --prefix todo-frontend ci && npm --prefix todo-frontend test -- --watchAll=false --ci
- UI E2E: node todo-frontend/selenium/login.test.js && node todo-frontend/selenium/additem.test.js
- Perf: k6 run performance/k6/todos_smoke.js -e BASE_URL=http://localhost:8080