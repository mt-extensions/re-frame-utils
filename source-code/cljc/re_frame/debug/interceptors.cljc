
(ns re-frame.debug.interceptors
    (:require [re-frame.core :as core]))

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @constant (map)
; {:after (function)
;  :before (function)
;  :id (keyword)}
(def debug (core/->interceptor :id :re-frame/debug :after (fn [context] (-> context :coeffects :event println)
                                                                        (-> context))))
