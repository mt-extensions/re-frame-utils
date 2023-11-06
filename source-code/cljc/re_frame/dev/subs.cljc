
(ns re-frame.dev.subs
    (:require [re-frame.core      :as core]
              [re-frame.dev.state :as state]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-db-reset-count
  ; @description
  ; - Returns the actual Re-Frame DB write count.
  ; - It only counts the DB writes when this function is subscribed!
  ;
  ; @return (integer)
  [db _]
  (swap! state/DB-RESET-COUNT inc)
  (->   @state/DB-RESET-COUNT))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @usage
; [:get-db-reset-count]
(core/reg-sub :get-db-reset-count get-db-reset-count)
