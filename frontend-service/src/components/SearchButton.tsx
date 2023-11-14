import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { useState } from "react";

export function SearchButton() {
  const [inputValue, setInputValue] = useState("");
  const [userInfo, setUserInfo] = useState("");

  const handleInputChange = (event: any) => {
    setInputValue(event.target.value);
  };

  // Event handler for the button click
  const handleButtonClick = () => {
    // We read from the useState and send to over to the microservice to get the user info
    console.log(inputValue);
  };
  setUserInfo("testing");

  return (
    <div className="flex w-full max-w-sm items-center space-x-2">
      <Input
        type="rut"
        placeholder="Ingrese Rut del usuario"
        onChange={handleInputChange}
      />
      <Button type="submit" onClick={handleButtonClick}>
        Subscribe
      </Button>
      {userInfo ? <p>{userInfo}</p> : null}
    </div>
  );
}
