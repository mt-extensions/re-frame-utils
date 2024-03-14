
(ns re-frame.debug.side-effects
    (:require [common-state.api :as common-state]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn set-debug-mode!
  ; @note
  ; DB and effect events registered with the [reg-event-db](/re-frame-extra/cljc/re-frame/extra/api.html#reg-event-db) and [reg-event-fx](/re-frame-extra/cljc/re-frame/extra/api.html#reg-event-fx) functions
  ; of the [re-frame-extra](/re-frame-extra/cljc/re-frame/extra/api.html) library, print their event handler to the console when the debug mode is turned on.
  ;
  ; @description
  ; Turns the debug mode on.
  ;
  ; @usage
  ; (set-debug-mode!)
  []
  (common-state/assoc-state! :re-frame.dev :debug-mode? true))

(defn quit-debug-mode!
  ; @description
  ; Turns the debug mode off.
  ;
  ; @usage
  ; (quit-debug-mode!)
  []
  (common-state/assoc-state! :re-frame.dev :debug-mode? false))

(defn toggle-debug-mode!
  ; @description
  ; Toggles the debug mode on/off.
  ;
  ; @usage
  ; (toggle-debug-mode!)
  []
  (common-state/update-state! :re-frame.dev :debug-mode? not))
