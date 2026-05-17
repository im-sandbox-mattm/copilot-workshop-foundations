import type { DashboardOverview } from './types'

const API_URL = 'http://localhost:8080/api/dashboard'

export async function fetchDashboardOverview(): Promise<DashboardOverview> {
  const response = await fetch(API_URL)

  if (!response.ok) {
    throw new Error(`Dashboard request failed with status ${response.status}`)
  }

  return (await response.json()) as DashboardOverview
}