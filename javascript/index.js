

function mergeSortedArray(arr1, arr2) {
    const mergedArray = [];
    const maxLength = arr1.length + arr2.length;

    if (arr1.length == 0) {
        return arr2;
    }
    if (arr2.length == 0) {
        return arr1;
    }

    let currentArr1Item = arr1[0];
    let currentArr2Item = arr2[0];
    let arr1Index = 0;
    let arr2Index = 0;


    for (let i = 0; i < maxLength; i++) {
        currentArr1Item = arr1[arr1Index];
        currentArr2Item = arr2[arr2Index];
        if ((currentArr2Item == undefined && currentArr1Item != undefined) || currentArr1Item < currentArr2Item) {
            mergedArray.push(currentArr1Item);
            arr1Index++;
        }
        if ((currentArr1Item == undefined && currentArr2Item != undefined) || currentArr1Item >= currentArr2Item) {
            mergedArray.push(currentArr2Item);
            arr2Index++;
        }
    }


    return mergedArray;
}


const arr1 = [0, 2, 4, 6, 9, 13, 13, 14];
const arr2 = [1, 2, 3, 4, 5, 5, 6, 6];

console.log(mergeSortedArray(arr1, arr2));

console.log([...arr1, ...arr2].sort((a, b) => a > b ? 1 : -1))


