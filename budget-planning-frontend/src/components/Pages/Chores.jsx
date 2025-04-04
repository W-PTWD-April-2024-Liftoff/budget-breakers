import React from 'react'
import ChoreCreationForm from '../ChoreCreationForm'
import LoginCheck from "../LoginCheck"

function Chores() {
  return (
    <div>
      <LoginCheck />
      <ChoreCreationForm />
    </div>
  )
}

export default Chores
