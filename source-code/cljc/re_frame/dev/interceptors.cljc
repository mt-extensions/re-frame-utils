
(ns re-frame.dev.interceptors
    (:require [re-frame.core      :as core]
              [re-frame.dev.state :as state]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn log-event-f
  ; @ignore
  ;
  ; @description
  ; Prints the event vector to the console in case the debug mode is turned on.
  ;
  ; @param (map) context
  ;
  ; @usage
  ; (reg-event-db :my-event [log-event-f] (fn [_ _] ...))
  ;
  ; @return (map)
  [context]
  (if @state/DEBUG-MODE? (-> context :coeffects :event println))
  (-> context))

; @constant (map)
; {:after (function)(opt)
;  :before (function)(opt)
;  :id (keyword)}
(def log-event! (core/->interceptor :id :re-frame.dev/log-event! :before log-event-f))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn debug-f
  ; @ignore
  ;
  ; @description
  ; Prints the dispatched event vector to the console.
  ;
  ; @param (map) context
  ;
  ; @usage
  ; (reg-event-db :my-event [debug-f] (fn [_ _] ...))
  ;
  ; @return (map)
  [context]
  (-> context :coeffects :event println)
  (-> context))

; @constant (map)
; {:after (function)(opt)
;  :before (function)(opt)
;  :id (keyword)}
(def debug! (core/->interceptor :id :re-frame.dev/debug! :after debug-f))
