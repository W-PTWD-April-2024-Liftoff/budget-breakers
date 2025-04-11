export const isAdult = (user) => {
    if(user?.accountType === "ADULT")
      sessionStorage.setItem("isAdult", true);
    return user?.accountType === "ADULT";
  };

  export const isCurrentUserEqualsAssignedUser = (chore, user) => {
    if (!chore.user || !user) return false;
    return chore.user.id === user.id;
  };