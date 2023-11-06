
(ns re-frame.dev.interceptors
    (:require [re-frame.core      :as core]
              [re-frame.dev.state :as state]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn log-event-f
  ; @ignore
  ;
  ; @description
  ; Prints the event vector to the console if the debug mode is turned on.
  ;
  ; @param (map) context
  ;
  ; @return (map)
  [context]
  (if @state/DEBUG-MODE? (-> context :coeffects :event println))
  (-> context))

; @constant (?)
(def log-event! (core/->interceptor :id :re-frame.dev/log-event! :before log-event-f))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn debug-f
  ; @ignore
  ;
  ; @description
  ; Prints the event vector to the console.
  ;
  ; @param (map) context
  ;
  ; @return (map)
  [context]
  (-> context :coeffects :event println)
  (-> context))

; @constant (?)
(def debug! (core/->interceptor :id :re-frame.dev/debug! :after debug-f))
