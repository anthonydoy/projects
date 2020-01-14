(ns closestp.core
  (:gen-class))

(defn d
  [[p q]]
  (+ (* (- (first p) (first q)) (- (first p) (first q))) 
     (* (- (second p) (second q)) (- (second p) (second q)))))

(defn base_case
  [Points]
  (let [n (count Points)]
    (if (>= n 2)
      (apply min-key d
             (for [i (range (- n 1)) :let [p (nth Points i)]
                   j (range (+ i 1) n) :let [q (nth Points j)]]
                  [p q])))))

(defn closest
  ([Points]
  (closest (sort-by first Points) (sort-by second Points)))
  ([Px Py]
  (let [n (count Px)]
    (if (< n 4)
      (base_case Px)

      ;; divide
      (let [[PxL PxR] (partition-all (Math/floor (/ n 2)) Px)
            line (first (last PxL))
            PyL (filter #(<= (first %) line) Py)
            PyR (filter #(> (first %) line) Py)

            ;; conquer
            [Lhalf1 Lhalf2] (closest PxL PyL)
            [Rhalf1 Rhalf2] (closest PxR PyR)
            bestp (min-key d [Lhalf1 Lhalf2] [Rhalf1 Rhalf2])
            bestd (d bestp)
            strip (filter #(<= (Math/abs (- line (first %))) bestd) Py)]

            ;; combine
            (if (> (count strip) 1)
              (apply min-key d
                (for [i (range 0 (count strip))
                      j (range (+ i 1) (+ i 7)) :while (< (+ i j) (count strip))]
                  [(nth strip i) (nth strip j)]))
              bestp))))))


(defn -main
  "I don't do a whole lot ... yet."
  [arg & args]
  ;(cond 
   ; (and (= func "-brute") (> arg 0))
   ;   (time (base_case (for [i (range arg)] [(rand) (rand)]))))
    ;(and (= func "-divide") (> arg 0))
      (time (closest (for [i (range arg)] [(rand) (rand)]))))
    ;:else (println "Usage: <-brute / -divide> <positive int>"))
