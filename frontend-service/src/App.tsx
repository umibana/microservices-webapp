import "./App.css";
import { ThemeProvider } from "./components/theme-provider";
import { MenubarFront } from "./components/MenubarFront";
import { SearchButton } from "./components/SearchButton";
import UserCreationForm from "./components/UserCreationForm";
import FileUpload from "./components/FileUpload";

function App() {
	return (
		<ThemeProvider defaultTheme="dark" storageKey="vite-ui-theme">
			<MenubarFront />
			<div className="flex flex-row gap-16 my-8">
				<SearchButton />
				<UserCreationForm />
				<FileUpload />
			</div>
		</ThemeProvider>
	);
}

export default App;
