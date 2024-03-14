
(ns re-frame.debug.env
    (:require [common-state.api :as common-state]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn debug-mode?
  ; @description
  ; Returns TRUE if the debug mode is turned on.
  ;
  ; @usage
  ; (debug-mode?)
  ; =>
  ; true
  ;
  ; @return (boolean)
  []
  (common-state/get-state :re-frame.dev :debug-mode?))
