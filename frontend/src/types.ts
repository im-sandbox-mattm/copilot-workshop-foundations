export type PreferredChannel = 'SMS' | 'Phone' | 'Email'

export interface CustomerSummary {
  fullName: string
  preferredChannel: PreferredChannel
}

export interface PetSummary {
  name: string
  species: string
  breed: string
  careFlag: string
}

export interface ClinicianSummary {
  fullName: string
  specialty: string
}

export interface AppointmentSummary {
  appointmentId: number
  startsAt: string
  status: string
  reason: string
  customer: CustomerSummary
  pet: PetSummary
  clinician: ClinicianSummary
  treatmentSummary: string
  followUpRequired: boolean
}

export interface ClinicianLoad {
  clinicianName: string
  specialty: string
  appointmentsToday: number
  openFollowUps: number
}

export interface DashboardOverview {
  clinicName: string
  locationLabel: string
  shiftSummary: string
  appointments: AppointmentSummary[]
  clinicianLoad: ClinicianLoad[]
}