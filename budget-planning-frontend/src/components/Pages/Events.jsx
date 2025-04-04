import React from 'react'
import CreateEvent from '../CreateEvent'
import LoginCheck from "../LoginCheck.jsx"

export default function Events() {
  return (
    <div>
      <LoginCheck />
      <CreateEvent />
    </div>
  )
}
