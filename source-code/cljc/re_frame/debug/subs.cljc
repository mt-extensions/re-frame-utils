
(ns re-frame.debug.subs
    (:require [common-state.api :as common-state]
              [re-frame.core    :as core]))

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
  (common-state/update-state! :re-frame.debug :db-reset-count inc)
  (common-state/get-state     :re-frame.debug :db-reset-count))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @usage
; [:get-db-reset-count]
(core/reg-sub :get-db-reset-count get-db-reset-count)
