import type { DashboardOverview } from './types'

const API_URL = 'http://localhost:8080/api/dashboard'

export async function fetchDashboardOverview(): Promise<DashboardOverview> {
  const requestId = crypto.randomUUID()
  const response = await fetch(API_URL, {
    headers: {
      'X-Request-ID': requestId,
    },
  })

  if (!response.ok) {
    const responseRequestId = response.headers.get('X-Request-ID') ?? requestId
    throw new Error(
      `Dashboard request failed with status ${response.status} (request ${responseRequestId})`,
    )
  }

  return (await response.json()) as DashboardOverview
}