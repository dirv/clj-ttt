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
      (str (name (next-player board)) "'s go, choose a square: "))))

(defn display [board]
  (str (board-string board) (play-message board)))

(defn read-move []
  (dec (read-string (read-line))))

(defn read-human [player]
  (println (str "Is player " (name player) " human? "))
  (if (= "y" (read-line))
    :human
    :computer))

(defn print-board [board]
  (println (display board)))

(defmulti make-player-move (fn [board x o] (if-x-is-next board x o)))
(defmethod make-player-move :human [board x o]
  (play-move board (read-move) (next-player board)))
(defmethod make-player-move :computer [board x o]
  (choose-next-move board (next-player board)))

(defn play-until-finish [board x o]
  (print-board board)
  (if-not (finished? board)
    (play-until-finish (make-player-move board x o) x o)))

(defn prompt-and-play [board]
  (let [x (read-human :X)
        o (read-human :O)]
    (play-until-finish board x o)))
