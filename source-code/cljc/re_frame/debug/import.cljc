
(ns re-frame.debug.import
    (:require [fruits.vector.api           :as vector]
              [re-frame.debug.env          :as env]
              [re-frame.debug.interceptors :as interceptors]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

(defn import-debug-interceptor
  ; @description
  ; Appends the debug interceptor to the given interceptors vector (if the debug mode is turned on).
  ;
  ; @param (vector) interceptors
  ;
  ; @usage
  ; (import-debug-interceptor [])
  ; =>
  ; [re-frame.debug/debug]
  ;
  ; @return (functions in vector)
  [interceptors]
  (if (env/debug-mode?)
      (-> interceptors (vector/conj-item interceptors/debug))
      (-> interceptors)))
