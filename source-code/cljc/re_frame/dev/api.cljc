
(ns re-frame.dev.api
    (:require [re-frame.dev.env          :as env]
              [re-frame.dev.interceptors :as interceptors]
              [re-frame.dev.side-effects :as side-effects]
              [re-frame.dev.state        :as state]
              [re-frame.dev.subs         :as subs]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (re-frame.dev.env/*)
(def get-event-handlers        env/get-event-handlers)
(def get-event-handler         env/get-event-handler)
(def event-handler-registered? env/event-handler-registered?)

; @redirect (re-frame.dev.interceptors/*)
(def log-event! interceptors/log-event!)
(def debug!     interceptors/debug!)

; @redirect (re-frame.dev.side-effects/*)
(def set-debug-mode!    side-effects/set-debug-mode!)
(def quit-debug-mode!   side-effects/quit-debug-mode!)
(def toggle-debug-mode! side-effects/toggle-debug-mode!)

; @redirect (re-frame.dev.subs/*)
(def get-db-reset-count subs/get-db-reset-count)

; @redirect (re-frame.dev.state/*)
(def DEBUG-MODE? state/DEBUG-MODE?)
