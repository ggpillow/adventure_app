# API Endpoints — Kolyma Adventure App Server

## Scenarios
- GET /api/scenarios — получить список всех сценариев
- GET /api/scenarios/{id} — получить сценарий по ID

## Schemes
- GET /api/schemes/scenario/{scenarioId} — получить список схем по ID сценария

## Endings
- GET /api/endings/scenario/{scenarioId} — получить все концовки по сценарию

## Paragraphs
- GET /api/paragraphs — получить все абзацы
- GET /api/paragraphs/{id} — получить абзац по ID

## Epigraphs
- GET /api/epigraphs — получить все эпиграфы
- GET /api/epigraphs/{id} — получить эпиграф по ID

---

*Все методы — только для чтения. Изменение данных возможно только с помощью администратора.*