let h = 1000;
let w = 1000;

let array_100x100: number[][] = Array(h).fill(0).map(() => Array(w).fill(0));

function sleep(ms: number) {
    return new Promise(resolve => setTimeout(resolve, ms));
}

async function next_gen(data: number[][], sum: number = 0) {
    await sleep(10);
    console.clear();

    if (sum > 100) {
        return data;
    }

    let new_data: number[][] = Array(h).fill(0).map(() => Array(w).fill(0));

    for (let i = 0; i < h; i++) {
        for (let x = 0; x < w; x++) {
            let count = count_ones_around(data, i, x);

            if (data[i][x] == 0) {
                if (count == 3) {
                    new_data[i][x] = 1;
                }
            } else if (data[i][x] == 1) {
                if (count == 2 || count == 3) {
                    new_data[i][x] = 1;
                } else {
                    new_data[i][x] = 0;
                }
            }
        }
    }

    console.log(new_data.map(row => row.join(" ")).join("\n"));

    return next_gen(new_data, sum + 1);
}

function count_ones_around(matrix: number[][], row: number, col: number) {
    let rows = matrix.length;
    let cols = matrix[0].length;

    let count = 0;

    for (let i = row - 1; i <= row + 1; i++) {
        for (let j = col - 1; j <= col + 1; j++) {
            if (
                i >= 0 && i < rows &&
                j >= 0 && j < cols &&
                (i != row || j != col) &&
                matrix[i][j] == 1
            ) {
                count += 1;
            }
        }
    }

    return count;
}

function insert_one_randomly(matrix: number[][], sum: number) {
    let rows = matrix.length;
    let cols = matrix[0].length;

    if (rows == 0 || cols == 0) {
        return matrix;
    }

    for (let i = 0; i < sum; i++) {
        let row_idx = Math.floor(Math.random() * rows);
        let col_idx = Math.floor(Math.random() * cols);

        if (matrix[row_idx][col_idx] == 1) {
            i -= 1;
            continue;
        }
        matrix[row_idx][col_idx] = 1;
    }

    return matrix;
}

next_gen(insert_one_randomly(array_100x100, Math.floor(w * h * 0.5)));
