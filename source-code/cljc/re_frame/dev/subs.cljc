
(ns re-frame.dev.subs
    (:require [re-frame.core      :as core]
              [re-frame.dev.state :as state]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn get-db-reset-count
  ; @note
  ; Only counts the db writes when this handler is subscribed!
  ;
  ; @description
  ; Returns the actual db write count.
  ;
  ; @usage
  ; (get-db-reset-count db [:my-handler])
  ;
  ; @usage
  ; (subscribe [:get-db-reset-count])
  ;
  ; @return (integer)
  [db _]
  (-> state/DB-RESET-COUNT (swap! inc))
  (-> state/DB-RESET-COUNT (deref)))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @usage
; [:get-db-reset-count]
(core/reg-sub :get-db-reset-count get-db-reset-count)
