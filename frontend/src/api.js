const BASE_URL = process.env.REACT_APP_API_URL;

export const getHello = async () => {
    const response = await fetch(`${BASE_URL}/hello`);
    if (!response.ok) {
        throw new Error('Network response was not ok');
    }
    return response.text();
};
