  import axiosClient from "./axiosClient.js";

  // POST /reservation  (requires ROLE_CUSTOMER)
  // request shape (ReservationRequestDTO):
  //   { firstName, secondName, reservationType: "SELF"|"OTHERS",
  //     totalNumberOfPeople, emailId, phoneNumber, reservationDate, reservationTime }
  // response shape (ReservationResponseDTO):
  //   { id, firstName, secondName, reservationType, totalNumberOfPeople,
  //     emailId, phoneNumber, reservationDate, reservationTime, validTill,
  //     tableNumber, bookedBy }
  export const createReservation = async (payload) => {
    const { data } = await axiosClient.post("/reservation", payload);
    return data;
  };
