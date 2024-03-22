
(ns re-frame.debug.api
    (:require [re-frame.debug.env          :as env]
              [re-frame.debug.import       :as import]
              [re-frame.debug.interceptors :as interceptors]
              [re-frame.debug.side-effects :as side-effects]
              [re-frame.debug.subs         :as subs]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @redirect (re-frame.debug.env/*)
(def debug-mode? env/debug-mode?)

; @redirect (re-frame.debug.import/*)
(def import-debug-interceptor import/import-debug-interceptor)

; @redirect (re-frame.debug.interceptors/*)
(def debug interceptors/debug)

; @redirect (re-frame.debug.side-effects/*)
(def set-debug-mode!    side-effects/set-debug-mode!)
(def quit-debug-mode!   side-effects/quit-debug-mode!)
(def toggle-debug-mode! side-effects/toggle-debug-mode!)

; @redirect (re-frame.debug.subs/*)
(def get-db-reset-count subs/get-db-reset-count)
