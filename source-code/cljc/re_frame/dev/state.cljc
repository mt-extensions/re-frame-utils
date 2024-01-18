
(ns re-frame.dev.state)

;; ----------------------------------------------------------------------------
;; ----------------------------------------------------------------------------

; @ignore
;
; @atom (integer)
(def DB-RESET-COUNT (atom 0))

; @atom (boolean)
;
; @usage
; (deref DEBUG-MODE?)
; =>
; true
(def DEBUG-MODE? (atom false))
