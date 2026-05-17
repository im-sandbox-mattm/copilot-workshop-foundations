import { useEffect, useState } from 'react'
import { fetchDashboardOverview } from './api'
import OwnerNoticePreview from './security/OwnerNoticePreview'
import type { DashboardOverview } from './types'
import './App.css'

function App() {
  const [dashboard, setDashboard] = useState<DashboardOverview | null>(null)
  const [errorMessage, setErrorMessage] = useState<string | null>(null)

  useEffect(() => {
    let cancelled = false

    fetchDashboardOverview()
      .then((nextDashboard) => {
        if (cancelled) {
          return
        }

        setDashboard(nextDashboard)
      })
      .catch((error: Error) => {
        if (cancelled) {
          return
        }

        setErrorMessage(error.message)
      })

    return () => {
      cancelled = true
    }
  }, [])

  if (errorMessage) {
    return (
      <main className="app-shell">
        <section className="hero-panel hero-panel--error">
          <p className="eyebrow">PetCare Operations</p>
          <h1>Clinic dashboard unavailable</h1>
          <p>
            Start the backend on port 8080 and refresh the page. Current error:
            {' '}
            {errorMessage}
          </p>
        </section>
      </main>
    )
  }

  if (!dashboard) {
    return (
      <main className="app-shell">
        <section className="hero-panel">
          <p className="eyebrow">PetCare Operations</p>
          <h1>Loading today&apos;s appointments</h1>
          <p>Fetching the day-shift dashboard from the Spring Boot API.</p>
        </section>
      </main>
    )
  }

  return (
    <main className="app-shell">
      <section className="hero-panel">
        <div>
          <p className="eyebrow">{dashboard.clinicName}</p>
          <h1>Day-shift appointment board</h1>
          <p className="hero-copy">
            {dashboard.locationLabel} keeps reception, clinicians, and follow-up work
            visible in one place.
          </p>
        </div>
        <div className="hero-summary">
          <p className="hero-summary__label">Operations note</p>
          <p>{dashboard.shiftSummary}</p>
        </div>
      </section>

      <section className="metrics-grid" aria-label="Clinician load">
        {dashboard.clinicianLoad.map((load) => (
          <article key={load.clinicianName} className="metric-card">
            <p className="metric-card__eyebrow">{load.specialty}</p>
            <h2>{load.clinicianName}</h2>
            <p className="metric-card__value">{load.appointmentsToday} appointments</p>
            <p className="metric-card__detail">{load.openFollowUps} follow-ups pending</p>
          </article>
        ))}
      </section>

      <section className="appointments-panel">
        <div className="appointments-panel__header">
          <div>
            <p className="eyebrow">Live queue</p>
            <h2>Today&apos;s appointments</h2>
          </div>
          <p>{dashboard.appointments.length} appointments loaded from the backend API</p>
        </div>

        <div className="appointment-grid">
          {dashboard.appointments.map((appointment) => (
            <article key={appointment.appointmentId} className="appointment-card">
              <div className="appointment-card__header">
                <div>
                  <p className="appointment-card__time">
                    {new Date(appointment.startsAt).toLocaleTimeString([], {
                      hour: '2-digit',
                      minute: '2-digit',
                    })}
                  </p>
                  <h3>{appointment.pet.name}</h3>
                </div>
                <span className="status-pill">{appointment.status.replaceAll('_', ' ')}</span>
              </div>

              <dl className="appointment-details">
                <div>
                  <dt>Owner</dt>
                  <dd>{appointment.customer.fullName}</dd>
                </div>
                <div>
                  <dt>Visit reason</dt>
                  <dd>{appointment.reason}</dd>
                </div>
                <div>
                  <dt>Clinician</dt>
                  <dd>
                    {appointment.clinician.fullName}
                    {' '}
                    <span>{appointment.clinician.specialty}</span>
                  </dd>
                </div>
                <div>
                  <dt>Care flag</dt>
                  <dd>{appointment.pet.careFlag}</dd>
                </div>
              </dl>

              <p className="appointment-card__summary">{appointment.treatmentSummary}</p>

              <div className="appointment-card__footer">
                <span>
                  {appointment.pet.species}
                  {' '}
                  •
                  {' '}
                  {appointment.pet.breed}
                </span>
                <span>{appointment.customer.preferredChannel}</span>
                {appointment.followUpRequired ? <strong>Follow-up needed</strong> : null}
              </div>
            </article>
          ))}
        </div>
      </section>

      <OwnerNoticePreview />
    </main>
  )
}

export default App
