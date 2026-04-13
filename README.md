# nbc-schedule
일정관리 앱입니다. 


<details>
<summary>📅 Schedule Management API</summary>

# 📅 Schedule Management API

Spring Boot 기반의 일정 관리 REST API입니다.

## 공통 사항

| 항목 | 내용 |
|------|------|
| Base URL | `/api` |
| Content-Type | `application/json` |
| 시각 포맷 | `yyyy-MM-dd'T'HH:mm:ss` |
| 비밀번호 | 응답에서 항상 제외 |

---

## API 목록

| 기능 | 메서드 | URL | 인증 |
|------|--------|-----|------|
| 일정 생성 | `POST` | `/api/schedules` | - |
| 전체 일정 조회 | `GET` | `/api/schedules` | - |
| 단건 일정 조회 | `GET` | `/api/schedules/{id}` | - |
| 일정 수정 | `PATCH` | `/api/schedules/{id}` | password |
| 일정 삭제 | `DELETE` | `/api/schedules/{id}` | password |

---

## API 상세

### 1. 일정 생성 `POST /api/schedules`

**Request**
```json
{
  "title": "스프링 공부",
  "content": "JPA Auditing 적용 방법 학습",
  "author": "김철수",
  "password": "1234"
}
```
**Response** `201 Created`
```json
{
  "id": 1,
  "title": "스프링 공부",
  "content": "JPA Auditing 적용 방법 학습",
  "author": "김철수",
  "createdAt": "2025-04-09T10:00:00",
  "updatedAt": "2025-04-09T10:00:00"
}
```

---

### 2. 전체 일정 조회 `GET /api/schedules`

- `author` 쿼리 파라미터로 작성자 필터링 가능
- 정렬 기준: `updatedAt` 내림차순

```
GET /api/schedules             → 전체 조회
GET /api/schedules?author=김철수 → 작성자 필터 조회
```

**Response** `200 OK`
```json
[
  {
    "id": 2,
    "title": "QueryDSL 공부",
    "author": "김철수",
    "createdAt": "2025-04-09T11:00:00",
    "updatedAt": "2025-04-09T12:30:00"
  }
]
```

> 결과 없을 시 빈 배열 `[]` 반환

---

### 3. 단건 일정 조회 `GET /api/schedules/{id}`

**Response** `200 OK`
```json
{
  "id": 1,
  "title": "스프링 공부",
  "content": "JPA Auditing 적용 방법 학습",
  "author": "김철수",
  "createdAt": "2025-04-09T10:00:00",
  "updatedAt": "2025-04-09T10:00:00"
}
```

---

### 4. 일정 수정 `PATCH /api/schedules/{id}`

- `title`, `author` 중 전달된 필드만 수정 (미전달 시 기존값 유지)
- `content`, `createdAt`은 수정 불가

**Request**
```json
{
  "title": "스프링 심화 공부",
  "author": "김영희",
  "password": "1234"
}
```

**Response** `200 OK`
```json
{
  "id": 1,
  "title": "스프링 심화 공부",
  "content": "JPA Auditing 적용 방법 학습",
  "author": "김영희",
  "createdAt": "2025-04-09T10:00:00",
  "updatedAt": "2025-04-09T13:00:00"
}
```

---

### 5. 일정 삭제 `DELETE /api/schedules/{id}`

**Request**
```json
{
  "password": "1234"
}
```

**Response** `200 OK`
```json
{
  "message": "일정이 삭제되었습니다."
}
```

---

## 에러 코드 관리

| 상황 | 상태 코드 | 메시지 |
|------|-----------|--------|
| 존재하지 않는 ID | `404 Not Found` | `해당 일정을 찾을 수 없습니다.` |
| 비밀번호 불일치 | `403 Forbidden` | `비밀번호가 일치하지 않습니다.` |
| 필수 필드 누락 | `400 Bad Request` | `필수 입력값이 누락되었습니다.` |
| 서버 오류 | `500 Internal Server Error` | `서버 내부 오류가 발생했습니다.` |

</details>


[1.png](https://github.com/js-kim-arc/nbc-schedule/blob/main/1.png)
Erd 참고용 사진입니다.!!
