import { useState } from 'react'

function OwnerNoticePreview() {
  const [ownerName, setOwnerName] = useState('Maya Patel')
  const [customNote, setCustomNote] = useState('Bring the current medication list to check-in.')

  return (
    <section className="preview-panel" aria-label="Owner notice preview">
      <div className="preview-panel__content">
        <div>
          <p className="eyebrow">Internal Admin Preview</p>
          <h2>Owner reminder message</h2>
          <p>
            This internal tool lets reception staff preview reminder wording before it is sent.
          </p>
        </div>

        <label className="preview-panel__field">
          <span>Owner name</span>
          <input value={ownerName} onChange={(event) => setOwnerName(event.target.value)} />
        </label>

        <label className="preview-panel__field">
          <span>Custom note</span>
          <textarea
            rows={4}
            value={customNote}
            onChange={(event) => setCustomNote(event.target.value)}
          />
        </label>
      </div>

      <div className="preview-panel__preview">
        <p className="preview-panel__label">Rendered preview</p>
        <section>
          <h3>Reminder for {ownerName}</h3>
          <p>{customNote}</p>
        </section>
      </div>
    </section>
  )
}

export default OwnerNoticePreview