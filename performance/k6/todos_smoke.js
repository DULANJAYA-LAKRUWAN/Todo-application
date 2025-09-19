// Simple k6 smoke test for GET /todos
import http from 'k6/http';
import { sleep, check } from 'k6';

export const options = {
  vus: 5,
  duration: '1m',
  thresholds: {
    http_req_failed: ['rate<0.01'], // <1% errors
    http_req_duration: ['p(95)<500'], // P95 < 500ms
  },
};

export default function () {
  const baseUrl = __ENV.BASE_URL || 'http://localhost:8080';
  const res = http.get(`${baseUrl}/todos`);
  check(res, {
    'status 200': (r) => r.status === 200,
  });
  sleep(1);
}