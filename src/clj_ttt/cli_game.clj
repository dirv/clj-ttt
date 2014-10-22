(ns clj-ttt.cli-game
  (:require [clj-ttt.board :refer :all]
            [clj-ttt.computer-player :refer :all]))

(defn output-board-line [square-strings]
  (str (clojure.string/join " " square-strings) "\n"))

(defn number-or-mark [index mark playable-squares]
  (if (some #{index} playable-squares)
    (str (inc index))
    mark))

(defn numbers-or-marks [board]
  (map-indexed #(number-or-mark %1 %2 (playable-squares board)) board))

(defn board-string [board]
  (apply str (map output-board-line (partition (size board) (numbers-or-marks board)))))

(defn play-message [board]
  (if (won? board)
    (str (last-player board) " wins!")
    (if (finished? board)
      (str "It's a draw!")
      (str (next-player board) "'s go, choose a square: "))))

(defn display [board]
  (str (board-string board) (play-message board)))

(defn read-move []
  (dec (read-string (read-line))))

(defn read-human [player]
  (print (str "Is player " player " human? "))
  (if (= "y" (read-line))
    :human
    :computer))

(defn print-board [board]
  (println (display board)))

(defn next-player-type [board x-type o-type]
  (if (= "X" (next-player board)) x-type o-type))

(defmulti make-player-move (fn [board x o] (next-player-type board x o)))
(defmethod make-player-move :human [board x o]
  (play-move board (read-move) (next-player board)))
(defmethod make-player-move :computer [board x o]
  (choose-next-move board (next-player board)))

(defn play-cli-move [board x o]
  (print-board board)
  (make-player-move board x o))

(defn play-until-finish [board x o]
  (let [new-board (play-cli-move board x o)]
    (if (finished? new-board)
      (print-board new-board)
      (play-until-finish new-board x o))))

(defn prompt-and-play [board]
  (let [x (read-human "X")
        o (read-human "O")]
    (play-until-finish board x o)))
