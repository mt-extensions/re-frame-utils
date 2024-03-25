
(ns re-frame.debug.subs
    (:require [re-frame.core :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @ignore
;
; @atom (integer)
(def RESET-COUNT (atom 0))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-db-reset-count
  ; @description
  ; Returns the write count of the db (occurred since this query was subscribed).
  ;
  ; @usage
  ; (get-db-reset-count db [:my-handler])
  ;
  ; @usage
  ; (subscribe [:get-db-reset-count])
  ;
  ; @return (integer)
  [db _]
  (swap! RESET-COUNT inc))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @usage
; [:get-db-reset-count]
(core/reg-sub :get-db-reset-count get-db-reset-count)
