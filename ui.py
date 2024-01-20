import tkinter as tk
import smyx
import threading
import numpy as np

# Global Variables
root = tk.Tk()
label = tk.Label(root, text="Welcome to my App")
xians=5
canvas = tk.Canvas(root, width=smyx.w *xians, height=smyx.h * xians)
data = smyx.insert_one_randomly(np.zeros((smyx.w, smyx.h), dtype=int), int(smyx.w * smyx.h * 0.5))
thread = threading.Thread()
stop = False

# Setup UI
root.title("My GUI App")
label.pack()
canvas.pack()

def start():
    global thread, stop
    stop = False
    thread = threading.Thread(target=run)
    thread.start()

def run():
    global data, stop
    if not stop:
        canvas.delete("all")
        data = smyx.next_gen(data)
        for i in range(smyx.h):
            for x in range(smyx.w):
                if data[x][i] == 1:
                    canvas.create_rectangle(
                        x * xians,
                        i *xians,
                        (x + 1) *xians,
                        (i + 1) * xians,
                        fill="blue",
                    )
        canvas.update()
        root.after(10, run)

def reset():
    global stop, data
    stop = True
    data = smyx.insert_one_randomly(data, int(smyx.w * smyx.h * 0.5))
    canvas.delete("all")

start_button = tk.Button(root, text="Start", command=start)
start_button.pack()

reset_button = tk.Button(root, text="Reset", command=reset)
reset_button.pack()

root.mainloop()


