import {
  PROCEED_PAYMENT_REQUEST,
  PROCEED_PAYMENT_SUCCESS,
  PROCEED_PAYMENT_FAILURE,
} from "./actionTypes";
import api from "../../config/api";


export const paymentScuccess =
  ({ paymentId, paymentLinkId, jwt }: { paymentId: string; paymentLinkId: string; jwt: string }) =>
    async (dispatch: any) => {
      dispatch({ type: PROCEED_PAYMENT_REQUEST });

      try {
        const response = await api.patch("/api/payments/proceed", null, {
          headers: { Authorization: `Bearer ${jwt}` },
          params: { paymentId, paymentLinkId },
        });

        dispatch({
          type: PROCEED_PAYMENT_SUCCESS,
          payload: response.data,
        });

        console.log("Payment", response.data);
      } catch (error) {
        console.log("Payment failed", error);
        const err = error as any;
        dispatch({
          type: PROCEED_PAYMENT_FAILURE,
          payload: err.response ? err.response.data : err.message,
        });
      }
    };
