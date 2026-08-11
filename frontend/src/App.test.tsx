import { render, screen, waitForElementToBeRemoved } from '@testing-library/react'
import { beforeEach, describe, expect, it, vi } from 'vitest'
import App from './App'
import { fetchDashboardOverview } from './api'
import type { DashboardOverview } from './types'

vi.mock('./api', () => ({
  fetchDashboardOverview: vi.fn(),
}))

const dashboardOverview: DashboardOverview = {
  clinicName: 'PetCare Operations',
  locationLabel: 'Harbor District Clinic',
  shiftSummary: 'Sample appointment data for the day shift.',
  appointments: [
    {
      appointmentId: 1002,
      startsAt: '2026-05-17T10:15:00',
      status: 'IN_ROOM',
      reason: 'Post-op wound check',
      customer: {
        fullName: 'Daniel Morris',
        preferredChannel: 'Phone',
      },
      pet: {
        name: 'Atlas',
        species: 'Cat',
        breed: 'Maine Coon',
        careFlag: 'High-stress handling',
      },
      clinician: {
        fullName: 'Dr. Priya Shah',
        specialty: 'Surgery',
      },
      treatmentSummary: 'Remove dressing and verify recovery notes.',
      followUpRequired: true,
    },
  ],
  clinicianLoad: [
    {
      clinicianName: 'Dr. Priya Shah',
      specialty: 'Surgery',
      appointmentsToday: 1,
      openFollowUps: 1,
    },
  ],
}

describe('App', () => {
  beforeEach(() => {
    vi.mocked(fetchDashboardOverview).mockResolvedValue(dashboardOverview)
  })

  it('renders dashboard appointment data from the API', async () => {
    render(<App />)

    expect(screen.getByText("Loading today's appointments")).toBeInTheDocument()

    await waitForElementToBeRemoved(() => screen.queryByText("Loading today's appointments"))

    expect(screen.getByRole('heading', { name: 'Day-shift appointment board' })).toBeInTheDocument()
    expect(screen.getByText('1 appointments loaded from the backend API')).toBeInTheDocument()
    expect(screen.getByText('Atlas')).toBeInTheDocument()
    expect(screen.getByText('Follow-up needed')).toBeInTheDocument()
  })
})
